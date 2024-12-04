package org.fungover.system2024.file;

import org.fungover.system2024.file.entity.File;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface FileRepository extends ListCrudRepository<File, Integer> {
    List<File> findByName(String name);
}
