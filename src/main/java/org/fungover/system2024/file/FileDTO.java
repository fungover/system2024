package org.fungover.system2024.file;

import org.fungover.system2024.file.entity.File;

public record FileDTO(Integer id, Integer owner, String originalName, String storedFilename ) {
    public static FileDTO fromFile(File file) {
        if (file == null) {
            throw new IllegalArgumentException("File is null");
        }
            return new FileDTO(
                    file.getId(),
                    file.getOwner().getId(),
                    file.getOriginalFilename(),
                    file.getStoredFilename()
            );
    }
}
