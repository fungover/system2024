package org.fungover.system2024.file;

import org.fungover.system2024.file.entity.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;

public interface FileRepository extends ListCrudRepository<File, Integer> {
    Page<File> findAll(Pageable pageable);
}
