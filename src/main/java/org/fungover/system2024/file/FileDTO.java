package org.fungover.system2024.file;

import org.fungover.system2024.file.entity.File;

public record FileDTO(String name, String fileUrl) {
    public static FileDTO fromFile(File file) {
        if (file == null) {
            System.out.println("file is null");
            return null;
        }
            return new FileDTO(
                    file.getName(),
                    file.getFileUrl()
            );
    }
}
