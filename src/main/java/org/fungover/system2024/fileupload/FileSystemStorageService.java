package org.fungover.system2024.fileupload;

import org.fungover.system2024.file.FileRepository;
import org.fungover.system2024.file.entity.File;
import org.fungover.system2024.fileupload.Exceptions.StorageException;
import org.fungover.system2024.fileupload.Exceptions.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    public FileSystemStorageService(@Value("${storage.location}") String location,
                                    FileRepository fileRepository) {
        this.rootLocation = Paths.get(location);
        this.fileRepository = fileRepository;
    }

    @Autowired
    private FileRepository fileRepository;



    @Override
    public void store(List<MultipartFile> files) {
        try{
            for(MultipartFile file : files) {
                if (file.isEmpty()) {
                    throw new StorageException("Filed to store empty file");
                }
                String sanitizedFilename = sanitizeFilename(Objects.requireNonNull(file.getOriginalFilename()));
                String storedFilename = generateStoredFilename(sanitizedFilename);

                Path destinationFile = this.rootLocation.resolve(
                                Paths.get(Objects.requireNonNull(storedFilename)))
                        .normalize().toAbsolutePath();

                if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                    //Security check
                    throw new StorageException("Cannot store file outside current directory");
                }

                // TODO: Change to auth userID
                int userId = 123;

                try (InputStream inputStream = file.getInputStream()) {
                    Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                    ////                // Save metadata to the database
                    File metadata = new File();
                    metadata.setOwner(userId);
                    metadata.setName(file.getOriginalFilename());
                    metadata.setStoredFilename(storedFilename);
                    fileRepository.save(metadata);
                } catch (IOException e) {
                    System.err.println("File upload failed: " + e.getMessage());
                    throw e; // Rethrow the exception or handle it as necessary
                }
            }

        } catch (IOException e){
            throw new StorageException("Cannot store file");
        }
    }

    @Override
    public Stream<Path> loadAll(){
        try{
            return Files.walk(this.rootLocation, 1)
                    .filter( path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e){
            throw new StorageException("Cannot load files");
        }
    }

    @Override
    public Path load(String filename){
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try{
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            }
            else throw new StorageException("Cannot load file");
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Cannot load file" + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Cannot create directories");
        }
    }

    private String sanitizeFilename(String originalFilename) {
        return originalFilename.replaceAll("[^a-zA-Z0-9.-]", "_");
    }

    private String generateStoredFilename(String sanitizedFilename) {
        String fileExtension = sanitizedFilename.substring(sanitizedFilename.lastIndexOf("."));
        return UUID.randomUUID().toString() + fileExtension;
    }
    // END BRACKET
}
