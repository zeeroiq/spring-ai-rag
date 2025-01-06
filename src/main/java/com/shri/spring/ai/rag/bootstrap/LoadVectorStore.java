package com.shri.spring.ai.rag.bootstrap;

import com.shri.spring.ai.rag.config.VectorStoreProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoadVectorStore implements CommandLineRunner {

    private final VectorStore vectorStore;
    private final VectorStoreProperties vectorStoreProperties;

    @Override
    public void run(String... args) throws Exception {
        if (Objects.requireNonNull(vectorStore.similaritySearch("Sportsman")).isEmpty()) {
            log.info("Loading document to vector-store");

            vectorStoreProperties.getDocument2load().forEach(doc -> {
                log.info("Loading document - {}", doc.getFilename());
                TikaDocumentReader documentReader = new TikaDocumentReader(doc);
                List<Document> documents = documentReader.get();
                TextSplitter textSplitter = new TokenTextSplitter();
                List<Document> splitDocs = textSplitter.apply(documents);
                vectorStore.add(splitDocs);
            });
        }

        log.info("Vector store loaded");
    }
}
