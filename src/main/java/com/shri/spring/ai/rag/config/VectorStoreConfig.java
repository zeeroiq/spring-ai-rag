package com.shri.spring.ai.rag.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.List;

@Slf4j
@Configuration
public class VectorStoreConfig {

    @Bean
    public SimpleVectorStore simpleVectorStore(EmbeddingModel embeddingModel, VectorStoreProperties vectorStoreProperties) {
        SimpleVectorStore store = new SimpleVectorStore(embeddingModel);
        File vectorStoreFile = new File(vectorStoreProperties.getVectorStoreDir());
        if (vectorStoreFile.exists()){
            store.load(vectorStoreFile);
        } else {
            log.info("Loading document into vector store");
            vectorStoreProperties.getDocument2load().forEach(doc -> {
                log.info("Loading document: {}", doc.getFilename());
                TikaDocumentReader documentReader = new TikaDocumentReader(doc);
                List<Document> docs = documentReader.get();
                TokenTextSplitter textSplitter = new TokenTextSplitter();
                List<Document> splitDocs = textSplitter.apply(docs);
                store.add(splitDocs);
            });
        }

        return store;
    }
}
