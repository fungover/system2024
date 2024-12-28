package org.fungover.system2024.file;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public Page<FileDTO> getAllFiles(@PageableDefault Pageable pageable) {
        return fileService.getAllFiles(pageable);
    }

     @GetMapping("/{name}")
    public List<FileDTO> getFilesByNameFuzzy(@PathVariable String name) {
        return fileService.getByNameFuzzy(name);
     }
}
