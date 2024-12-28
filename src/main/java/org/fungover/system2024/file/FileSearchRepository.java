package org.fungover.system2024.file;

import org.fungover.system2024.file.entity.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface FileSearchRepository {
    Page<File> findByNameFuzzy(String name, Pageable pageable);
}
