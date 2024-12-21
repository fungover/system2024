package org.fungover.system2024.fileupload;

import org.fungover.system2024.file.FileDTO;
import org.fungover.system2024.file.entity.File;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface StorageService {



    void store(List<MultipartFile> file);

    void deleteAll();

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    List<FileDTO> getListOfFiles(Integer userId);

    File getFileData(Integer fileId);

    ResponseEntity<ByteArrayResource> loadAllResourseAsZip(List<Integer> fileIds);

}