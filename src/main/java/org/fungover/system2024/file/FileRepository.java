package org.fungover.system2024.file;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.fungover.system2024.file.entity.File;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface FileRepository extends ListCrudRepository<File, Integer> {

    List<File> findAllByOwner(Integer owner);

    String findStoredFilenameById(Integer id);

    File getFileById(Integer fileId);
}
