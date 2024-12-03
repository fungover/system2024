package org.fungover.system2024.fileupload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/fileupload")
public class FileUploadController {

    @Value("${upload.directory}")
    private String uploadDirectory;

    @Value("${download.directory}")
    private String downloadDirectory;

    @PostMapping
    public String handleFileUpload(@RequestParam("files") List<MultipartFile> files, Model model) {
        String folderName = getFolderName();
        Path uploadFolderPath = Paths.get(uploadDirectory, folderName);

        // Create the upload directory if it doesn't exist
        try {
            Files.createDirectories(uploadFolderPath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create upload directory", e);
        }

        int uploadedFileCount = 0;

        for (MultipartFile file : files) {
            if (!file.isEmpty() && file.getOriginalFilename() != null && file.getOriginalFilename().endsWith(".gpx")) {
                try {
                    Path filePath = uploadFolderPath.resolve(file.getOriginalFilename());
                    try (InputStream inputStream = file.getInputStream();
                            FileOutputStream outputStream = new FileOutputStream(filePath.toFile())) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                    }
                    uploadedFileCount++;
                } catch (IOException e) {
                    throw new RuntimeException("Failed to save file", e);
                }
            }
        }

        // Simulate running the goodHunt.main method
        try {
            fileuploader.main(new String[] { folderName });
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Construct download URL
        String csvFileName = "csv" + folderName + ".csv";
        String downloadUrl = "/fileupload/download/" + csvFileName;

        // Add response data to the model
        model.addAttribute("uploadedFileCount", uploadedFileCount);
        model.addAttribute("downloadUrl", downloadUrl);

        return "uploadResult"; // Name of the Thymeleaf template for showing the result
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(downloadDirectory, fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("File not found: " + fileName);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error during file download", e);
        }
    }

    private static String getFolderName() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddHHmmssSSSS");
        Date currentDate = new Date();
        return dateFormat.format(currentDate).substring(0, 12);
    }
}
