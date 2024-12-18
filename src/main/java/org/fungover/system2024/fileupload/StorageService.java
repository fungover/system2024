package org.fungover.system2024.fileupload;

import org.fungover.system2024.file.entity.File;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface StorageService {


    void init();

    void store(List<MultipartFile> file);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();

    String getSafeName(Integer fileId);

    File getFileData(Integer fileId);
}