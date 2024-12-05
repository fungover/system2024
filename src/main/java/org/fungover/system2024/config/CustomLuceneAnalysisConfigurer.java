package org.fungover.system2024.config;

import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurationContext;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurer;

public class CustomLuceneAnalysisConfigurer implements LuceneAnalysisConfigurer {
    @Override
    public void configure(LuceneAnalysisConfigurationContext context) {
        try {
        context.analyzer("text_analyzer").custom()
                .tokenizer("standard")
                .tokenFilter("lowercase");
    } catch (Exception e) {
            System.out.println("An error occurred trying to build the search index: " + e);
        }
    }
}
