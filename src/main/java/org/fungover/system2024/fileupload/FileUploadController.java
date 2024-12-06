package org.fungover.system2024.fileupload;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/api/files")
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

    @PostMapping("/")
    public String handleMultiFileUpload(@RequestParam("files") List<MultipartFile> files, RedirectAttributes redirectAttributes) {
        try {

            for (MultipartFile file : files) {
                storageService.store(file);
                redirectAttributes.addFlashAttribute("message", "File uploaded successfully");
            }
            return "redirect:/";
//            return "All files successfully uploaded";
        } catch (Exception e) {
            return "Something went wrong while uploading files";
        }
    }
}
