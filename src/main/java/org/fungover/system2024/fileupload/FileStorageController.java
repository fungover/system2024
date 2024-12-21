package org.fungover.system2024.fileupload;

import java.util.*;

import org.fungover.system2024.file.FileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/api/files/")
public class FileStorageController {

    @Autowired

    StorageService storageService;

    public FileStorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    // TODO: Download function,
    //  * needs to add security check of user
    //  * change to POST request with List to have better UI

    @GetMapping("/list/{id}")
    public ResponseEntity getAllUsersFileNames(@PathVariable("id") Integer userId) {
        System.out.println(userId);
        List<FileDTO> listOfNames = storageService.getListOfFiles(userId);
        return ResponseEntity.ok(listOfNames);
    }

    @PostMapping("/fetch/")
    @ResponseBody
    public ResponseEntity<ByteArrayResource> fetchMultipleFiles(@RequestBody Map<String, List<Integer>> payload) {
        List<Integer> fileIds = payload.get("request");
        return storageService.loadAllResourseAsZip(fileIds);
    }


    @PostMapping("/")
    public ResponseEntity<String> handleMultiFileUpload(@RequestParam("files") List<MultipartFile> files) {
        try {
            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    return ResponseEntity.badRequest().body("Filed to store empty file");
                }
            }
            storageService.store(files);
            return ResponseEntity.ok().body("All files uploaded successfully\n");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error while uploading files\n");
        }
    }
}
