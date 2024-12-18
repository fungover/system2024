package org.fungover.system2024.fileupload;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import jakarta.servlet.http.HttpServletRequest;
import org.fungover.system2024.file.entity.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/api/files/")
public class FileStorageController {


    @Value("${storage.location}")
    private String storageLocation;

@Autowired

    StorageService storageService;

    public FileStorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    // TODO: Download function,
    //  * needs to add security check of user
    //  * change to POST request with List to have better UI
    @GetMapping("/fetch/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);

        if (file == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
////    @Value("${storage.location}")
//    @PostMapping("/fetch/")
//    @ResponseBody
//    public ResponseEntity<Resource> serveFile(@RequestParam("request") List<Integer> fileIds) {
//        for (Integer fileId : fileIds){
//            /*
//            fetch stored name with id
//            fetch name
//            fetch file
//            rename file
//            send file
//             */
//            File getFileData = storageService.getFileData(fileId);
//        Path fetchFileLocation = storageService.load(getFileData.getStoredFilename());
//        Resource file = storageService.loadAsResource(fetchFileLocation.toString());
//
//            Files.copy(file, fetchFileLocation, StandardCopyOption.REPLACE_EXISTING);
//
//            if (file == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
//        }
//        return ResponseEntity.notFound().build();
//    }
@PostMapping("/fetch/")
@ResponseBody
public ResponseEntity<Resource> serveFiles(@RequestParam("request") List<Integer> fileIds) {
    try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
         ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {

        for (Integer fileId : fileIds) {
            // Fetch file data
            File fileData = storageService.getFileData(fileId);
            if (fileData == null) {
                continue; // Skip invalid file IDs
            }

            // Load file as Resource
            Path filePath = storageService.load(fileData.getStoredFilename());
            Resource resource = storageService.loadAsResource(filePath.toString());

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


    @PostMapping("/")
    public ResponseEntity<String> handleMultiFileUpload(@RequestParam("files") List<MultipartFile> files) {
        try {
            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                        return ResponseEntity.badRequest().body("Filed to store empty file");
                    }
                System.out.println("Ctrl: " + file.getOriginalFilename());
                }
                storageService.store(files);
            return ResponseEntity.ok().body("All files uploaded successfully\n");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error while uploading files\n");
        }
    }


    @GetMapping("/token")
    public void showToken(HttpServletRequest request) {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        System.out.println("CSRF Token: " + csrfToken.getToken());
    }


    public static void logCsrfToken(HttpServletRequest request) {
        HttpSessionCsrfTokenRepository csrfTokenRepository = new HttpSessionCsrfTokenRepository();
        CsrfToken csrfToken = csrfTokenRepository.generateToken(request);

        System.out.println("CSRF Token: " + csrfToken.getToken());
    }

    @GetMapping("/test-csrf")
    public ResponseEntity<String> testCsrf(HttpServletRequest request) {
        HttpSessionCsrfTokenRepository csrfTokenRepository = new HttpSessionCsrfTokenRepository();
        CsrfToken csrfToken = csrfTokenRepository.generateToken(request);

        return ResponseEntity.ok("CSRF Token: " + csrfToken.getToken());
    }

}
