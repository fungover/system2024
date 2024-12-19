package org.fungover.system2024.file;

import org.springframework.stereotype.Service;
import org.fungover.system2024.controller.LoginController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {
    private FileRepository fileRepository;
    private FileSearchRepository fileSearchRepository;
    private final Logger logger = LoggerFactory.getLogger(FileService.class);

    public FileService(FileRepository fileRepository, FileSearchRepository fileSearchRepository) {
        this.fileRepository = fileRepository;
        this.fileSearchRepository = fileSearchRepository;
    }

    public List<FileDTO> getAllFiles() {

        if (fileRepository.findAll().isEmpty()) {
            logger.info("File list is empty");
        }
        return fileRepository.findAll()
                .stream()
                .map(FileDTO::fromFile)
                .toList();
    }

    public List<FileDTO> getByNameFuzzy(String name) {
        List<FileDTO> files = fileSearchRepository.findByNameFuzzy(name)
                .stream()
                .map(FileDTO::fromFile)
                .collect(Collectors.toUnmodifiableList());

        if (files.isEmpty()) {
            throw new IllegalArgumentException("No such files found");
        }

        return files;
    }
}