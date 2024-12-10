package org.fungover.system2024.fileupload;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import org.fungover.system2024.file.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
//@RequestMapping("/api/files/")
public class FileUploadController {
    @Autowired

    StorageService storageService;

    public FileUploadController(StorageService storageService) {
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

//    @PostMapping("/")
//    public String handleMultiFileUpload(@RequestParam("files") List<MultipartFile> files, RedirectAttributes redirectAttributes) {
//        try {
//
//            for (MultipartFile file : files) {
//                storageService.store(file);
//                redirectAttributes.addFlashAttribute("message", "File uploaded successfully");
//            }
//            return "redirect:/";
////            return "All files successfully uploaded";
//        } catch (Exception e) {
//            return "Something went wrong while uploading files";
//        }
//    }

    @PostMapping("/")
    public ResponseEntity<String> handleMultiFileUpload(@RequestParam("files") List<MultipartFile> files) {
        try {
            for (MultipartFile file : files) {
                System.out.println(file.getOriginalFilename());
                storageService.store(file); // Store each file using your service
            }
            return ResponseEntity.ok().body("All files uploaded successfully\n");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error while uploading files\n");
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> handleFileUpload(@RequestParam("files") List<MultipartFile> files) {
        List<String> uploadedFiles = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                if (!isValidFile(file)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid file type: " + file.getOriginalFilename());
                }

                String sanitizedFilename = sanitizeFilename(file.getOriginalFilename());
                String storedFilename = generateStoredFilename(sanitizedFilename);

                // Save the file to disk
                Path targetLocation = Paths.get("uploads/" + storedFilename);
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

//                // Save metadata to the database
//                FileRepository metadata = new FileRepository();
//                metadata.setOriginalFilename(file.getOriginalFilename());
//                metadata.setStoredFilename(storedFilename);
//                metadata.setFilePath(targetLocation.toString());
//                uploadedFileRepository.save(metadata);
                    storageService.store(file);
//                uploadedFiles.add(file.getOriginalFilename() + " uploaded successfully");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file: " + file.getOriginalFilename());
            }
        }
        return ResponseEntity.ok(uploadedFiles);
    }


    private String sanitizeFilename(String originalFilename) {
        return originalFilename.replaceAll("[^a-zA-Z0-9.-]", "_");
    }

    private String generateStoredFilename(String sanitizedFilename) {
        String fileExtension = sanitizedFilename.substring(sanitizedFilename.lastIndexOf("."));
        return UUID.randomUUID().toString() + fileExtension;
    }

    private boolean isValidFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && (contentType.equals("text/plain") || contentType.equals("application/pdf"));
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
