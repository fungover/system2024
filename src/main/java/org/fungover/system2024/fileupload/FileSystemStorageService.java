package org.fungover.system2024.fileupload;

import org.fungover.system2024.file.FileDTO;
import org.fungover.system2024.file.FileRepository;
import org.fungover.system2024.file.entity.File;
import org.fungover.system2024.fileupload.Exceptions.StorageException;
import org.fungover.system2024.fileupload.Exceptions.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
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
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileSystemStorageService implements StorageService {


    private final FileRepository fileRepository;
    private final Path fileLocation;

    public FileSystemStorageService(FileRepository fileRepository, @Value("${storage.location}") String storageLocation) {
        this.fileRepository = fileRepository;
        this.fileLocation = Paths.get(storageLocation);
    }


    @Override
    public File getFileData(Integer fileId) {
        return fileRepository.getFileById(fileId);
    }

    @Override
    public void store(List<MultipartFile> files) {
        try {
            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    throw new StorageException("Filed to store empty file");
                }
                String sanitizedFilename = sanitizeFilename(Objects.requireNonNull(file.getOriginalFilename()));
                String storedFilename = generateStoredFilename(sanitizedFilename);

                Path destinationFile = fileLocation.resolve(
                                Paths.get(Objects.requireNonNull(storedFilename)))
                        .normalize().toAbsolutePath();

                if (!destinationFile.getParent().equals(fileLocation.toAbsolutePath())) {
                    //Security check
                    throw new StorageException("Cannot store file outside current directory");
                }

                // TODO: Change to auth userID
                int userId = 123;

                try (InputStream inputStream = file.getInputStream()) {
                    Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                    // Save metadata to the database
                    File metadata = new File();
                    metadata.setOwner(userId);
                    metadata.setOriginalFilename(file.getOriginalFilename());
                    metadata.setStoredFilename(storedFilename);
                    fileRepository.save(metadata);
                } catch (IOException e) {
                    System.err.println("File upload failed: " + e.getMessage());
                    throw e; // Rethrow the exception or handle it as necessary
                }
            }

        } catch (IOException e) {
            throw new StorageException("Cannot store file" + e.getMessage());
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(fileLocation, 1)
                    .filter(path -> !path.equals(fileLocation))
                    .map(fileLocation::relativize);
        } catch (IOException e) {
            throw new StorageException("Cannot load files");
        }
    }

    @Override
    public Path load(String filename) {
        return fileLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else throw new StorageException("Cannot load file");
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Cannot load file" + filename, e);
        }
    }

    @Override
    public ResponseEntity<ByteArrayResource> loadAllResourseAsZip(List<Integer> fileIds) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {

            for (Integer fileId : fileIds) {
                // Fetch file data
                File fileData = fileRepository.getFileById(fileId);
                if (fileData == null) {
                    continue; // Skip invalid file IDs
                }

                // Load file as Resource
                Path filePath = load(fileData.getStoredFilename());
                Resource resource = loadAsResource(fileData.getStoredFilename());

                if (resource.exists() && resource.isReadable()) {
                    // Add file to ZIP
                    zipOutputStream.putNextEntry(new ZipEntry(fileData.getOriginalFilename()));
                    Files.copy(filePath, zipOutputStream);
                    zipOutputStream.closeEntry();
                }
            }

            zipOutputStream.finish();

            // Return the ZIP file as a ResponseEntity
            ByteArrayResource zipResource = new ByteArrayResource(byteArrayOutputStream.toByteArray());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"files.zip\"")
                    .body(zipResource);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }


    @Override
    public List<FileDTO> getListOfFiles(Integer userId) {
        List<FileDTO> userFiles = fileRepository.getAllByOwner(userId).stream()
                .map(FileDTO::fromFile)
                .toList();
        return userFiles;
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(fileLocation.toFile());
    }


    private String sanitizeFilename(String originalFilename) {
        if (originalFilename.contains("..") || originalFilename.contains("/") || originalFilename.contains("\\")) {
            throw new IllegalArgumentException("Invalid filename: " + originalFilename);
        }
        return originalFilename.replaceAll("[^a-zA-Z0-9.-]", "_");
    }

    private String generateStoredFilename(String sanitizedFilename) {
        String fileExtension = sanitizedFilename.substring(sanitizedFilename.lastIndexOf("."));
        return UUID.randomUUID().toString() + fileExtension;
    }
    // END BRACKET
}
