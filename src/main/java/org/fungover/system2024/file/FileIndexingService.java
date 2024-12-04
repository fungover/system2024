package org.fungover.system2024.file;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.fungover.system2024.file.entity.File;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class FileIndexingService implements ApplicationRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Override
    public void run(ApplicationArguments args) {
        startIndexing(File.class);
    }

    public <T> void startIndexing(Class<T> entityType) {
        transactionTemplate.executeWithoutResult(transactionStatus -> {
            try {
                SearchSession searchSession = Search.session(entityManager);
                MassIndexer indexer = searchSession.massIndexer(entityType);
                indexer.startAndWait();
            } catch (InterruptedException e) {
                System.out.println("An error occurred trying to build the search index: " + e);
                Thread.currentThread().interrupt();
            }
        });
    }
}