package org.fungover.system2024.file;

import org.fungover.system2024.file.entity.File;
import org.fungover.system2024.user.entity.User;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends ListCrudRepository<File, Integer> {

    List<File> getAllByOwner(User owner);

    File getFileById(Integer fileId);

    Optional<String> findStoredFilenameById(Integer id);

    String getStoredFilenameById(Integer id);
}