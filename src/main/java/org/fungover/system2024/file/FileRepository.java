package org.fungover.system2024.file;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.fungover.system2024.file.entity.File;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends ListCrudRepository<File, Integer> {

    List<File> getAllByOwner(Integer owner);

    File getFileById(Integer fileId);

    Optional<String> findStoredFilenameById(Integer id);

    String getStoredFilenameById(Integer id);
}
