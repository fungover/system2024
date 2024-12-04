package org.fungover.system2024.file;

import org.fungover.system2024.file.entity.File;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileRepository extends ListCrudRepository<File, Integer> {
    /* Fuzzy Search, This will return results that are kinda like the search param
    @Query("SELECT f FROM File f WHERE LOWER(f.name) LIKE LOWER(CONCAT('%',:name,'%'))")
    List<File> findByNameFuzzy(@Param("name") String name);*/
}
