package org.fungover.system2024.file;

import org.fungover.system2024.file.entity.File;

public record FileDTO(String name, String fileUrl) {
    public static FileDTO fromFile(File file) {
        return new FileDTO(
                file.getName(),
                file.getFileUrl()
        );
    }

    public String getName() {
        return name;
    }

    public String getFileUrl() {
        return fileUrl;
    }
}
