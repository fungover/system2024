package org.fungover.system2024.file;

import org.fungover.system2024.file.entity.File;
import org.springframework.data.repository.ListCrudRepository;

public interface FileRepository extends ListCrudRepository<File, Integer> {}
