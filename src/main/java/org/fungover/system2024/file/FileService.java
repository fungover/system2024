package org.fungover.system2024.file;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {

    FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public List<FileDTO> getByName(String name) {
        List<FileDTO> files = fileRepository.findByName(name)
                .stream()
                .map(FileDTO::fromFile)
                .collect(Collectors.toList());

        if (files.isEmpty()) {
            throw new IllegalArgumentException("No such files found");
        }

        return files;
    }
}
