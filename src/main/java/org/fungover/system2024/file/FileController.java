package org.fungover.system2024.file;

import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/files")
@Validated
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public Page<FileDTO> getAllFiles(@PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        return fileService.getAllFiles(pageable);
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getFilesByNameFuzzy(
            @PathVariable @Size(min = 1, max = 255, message = "File name must be between 1 and 255 characters") String name,
            @PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        try {
            Page<FileDTO> files = fileService.getByNameFuzzy(name, pageable);
            if(files.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(files);
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
