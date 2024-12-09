package org.fungover.system2024.file;

import org.fungover.system2024.file.entity.File;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileSearchRepository {
    List<File> findByNameFuzzy(String name);
}
