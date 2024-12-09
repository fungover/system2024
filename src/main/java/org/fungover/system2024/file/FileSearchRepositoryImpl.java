package org.fungover.system2024.file;

import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.hibernate.search.engine.search.query.SearchResult;
import org.fungover.system2024.file.entity.File;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class FileSearchRepositoryImpl implements FileSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<File> findByNameFuzzy(String name) {

        SearchSession searchSession = Search.session(entityManager);

        SearchResult<File> searchResult = searchSession.search(File.class)
                .where(f -> f.match()
                        .field("name")
                        .matching(name)
                        .fuzzy(2)) // This setting decides how lax the fuzzy is. If you increase this -
                // - it will be less strict when searching. So play around with this setting if you want to.
                .fetch(20);

        return searchResult.hits();
    }
}
