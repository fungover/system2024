package org.fungover.system2024.file;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {

    FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public List<FileDTO> getAllFiles() {
        return fileService.getAllFiles();
    }

     @GetMapping("/{name}")
    public List<FileDTO> getFilesByNameFuzzy(@PathVariable String name) {
        return fileService.getByNameFuzzy(name);
     }
}
