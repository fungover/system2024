package org.fungover.system2024.fileupload;

import org.fungover.system2024.file.FileDTO;
import org.fungover.system2024.file.FileRepository;
import org.fungover.system2024.file.entity.File;
import org.fungover.system2024.fileupload.Exceptions.StorageException;
import org.fungover.system2024.fileupload.Exceptions.StorageFileNotFoundException;
import org.fungover.system2024.user.entity.User;
import org.fungover.system2024.user.repository.UserRepository;
import org.hibernate.cache.spi.support.StorageAccess;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
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
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileSystemStorageService implements StorageService {


    private final FileRepository fileRepository;
    private final Path fileLocation;
    private final UserRepository userRepository;

    public FileSystemStorageService(FileRepository fileRepository, @Value("${storage.location}") String storageLocation, UserRepository userRepository) {
        this.fileRepository = fileRepository;
        this.fileLocation = Paths.get(storageLocation);
        this.userRepository = userRepository;
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

                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (!(authentication.getPrincipal() instanceof OAuth2User oauth2User)) {
                    throw new StorageException("User not authenticated");
                }
                Integer userId = oauth2User.getAttribute("id");
                assert userId != null;
                User activeuser = userRepository.findById(userId)
                        .orElseThrow(() -> new StorageException("User not found"));

                try (InputStream inputStream = file.getInputStream()) {
                    Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                    // Save metadata to the database
                    File metadata = new File();
                    metadata.setOwner(activeuser);
                    metadata.setOriginalFilename(file.getOriginalFilename());
                    metadata.setStoredFilename(storedFilename);
                    fileRepository.save(metadata);
                } catch (IOException e) {
                    System.err.println("File upload failed: " + e.getMessage());
                    throw e; // Rethrow the exception or handle it as necessary
                }
            }

        } catch (IOException e) {
            throw new StorageException("Cannot store file: " + e.getMessage().replaceAll("[\\r\\n]", ""));
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

//    @Override
//    public ResponseEntity<ByteArrayResource> loadAllResourseAsZip(List<Integer> fileIds) {
//        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//             ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {
//
//            for (Integer fileId : fileIds) {
//                // Fetch file data
//                File fileData = fileRepository.getFileById(fileId);
//                if (fileData == null) {
//                    continue; // Skip invalid file IDs
//                }
//
//                // Load file as Resource
//                Path filePath = load(fileData.getStoredFilename());
//                Resource resource = loadAsResource(fileData.getStoredFilename());
//
//                if (resource.exists() && resource.isReadable()) {
//                    // Add file to ZIP
//                    zipOutputStream.putNextEntry(new ZipEntry(fileData.getOriginalFilename()));
//                    Files.copy(filePath, zipOutputStream);
//                    zipOutputStream.closeEntry();
//                }
//            }
//
//            zipOutputStream.finish();
//
//            // Return the ZIP file as a ResponseEntity
//            ByteArrayResource zipResource = new ByteArrayResource(byteArrayOutputStream.toByteArray());
//
//            return ResponseEntity.ok()
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"files.zip\"")
//                    .body(zipResource);
//
//        } catch (IOException e) {
//
//            Logger logger = Logger.getLogger(getClass().getName());
//            logger.log(Level.SEVERE, "An error occurred", e);
//
//            return ResponseEntity.internalServerError().build();
//        }
//    }

    @Override
    public ResponseEntity<ByteArrayResource> loadAllResourcesAsZip(List<Integer> fileIds) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof OAuth2User oauth2User)) {
            throw new StorageException("User not authenticated");
        }

        Integer userId = oauth2User.getAttribute("id");
        if (userId == null) {
            throw new StorageException("User ID not found in authentication context");
        }

        User activeUser = userRepository.findById(userId)
                .orElseThrow(() -> new StorageException("User not found"));

        List<File> userFiles = fileRepository.getAllByOwner(activeUser);
        if (userFiles.isEmpty()) {
            throw new StorageFileNotFoundException("No files found for the user");
        }

        boolean allFilesAuthorized = fileIds.stream()
                .allMatch(requestedId -> userFiles.stream()
                        .anyMatch(userFile -> userFile.getId().equals(requestedId)));

        if (!allFilesAuthorized) {
            throw new StorageFileNotFoundException("User is not authorized for all requested files");
        }

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {

            for (Integer fileId : fileIds) {
                // Fetch file data
                File fileData = fileRepository.getFileById(fileId);
                if (fileData == null) {
                    Logger.getLogger(getClass().getName())
                            .log(Level.WARNING, "Invalid file ID: {0}", 
                                String.valueOf(fileId).replaceAll("[\n\r\t]", "_"));
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
                } else {
                    Logger.getLogger(getClass().getName())
                            .log(Level.WARNING, "File not readable or does not exist: {0}", fileData.getStoredFilename());
                }
            }

            zipOutputStream.finish();

            // Return the ZIP file as a ResponseEntity
            ByteArrayResource zipResource = new ByteArrayResource(byteArrayOutputStream.toByteArray());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"files.zip\"")
                    .body(zipResource);

        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "An error occurred while creating the ZIP file", e);

            return ResponseEntity.internalServerError().build();
        }
    }


    @Override
    public List<FileDTO> getListOfFiles() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof OAuth2User oauth2User)) {
            throw new StorageException("User not authenticated");
        }

        Integer userId = oauth2User.getAttribute("id");
        if (userId == null) {
            throw new StorageException("User ID not found in authentication context");
        }

        User activeUser = userRepository.findById(userId)
                .orElseThrow(() -> new StorageException("User not found"));

        List<File> userFiles = fileRepository.getAllByOwner(activeUser);
        if (userFiles.isEmpty()) {
            throw new StorageFileNotFoundException("No files found for the user");
        }

        return fileRepository.getAllByOwner(activeUser).stream()
                .map(FileDTO::fromFile)
                .toList();
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
