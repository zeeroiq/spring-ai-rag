package com.shri.spring.ai.rag.service.impl;

import com.shri.spring.ai.rag.model.Answer;
import com.shri.spring.ai.rag.model.Question;
import com.shri.spring.ai.rag.service.AIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class MistralAIServiceImpl implements AIService {

    private final ChatModel chatModel;
    private final SimpleVectorStore vectorStore;

    @Value("classpath:/templates/prompt-template-rag-meta.st")
    private Resource promptTemplateRag;

    @Override
    public Answer askQuestion(Question question) {
        List<Document> documents = vectorStore.similaritySearch(SearchRequest.builder().query(question.question()).topK(4).build());
        assert documents != null;
        List<String> contentList = documents.stream().map(Document::getText).toList();
        PromptTemplate promptTemplate = new PromptTemplate(promptTemplateRag);
        Prompt prompt = promptTemplate.create(Map.of("input", question.question(), "documents", String.join("\n", contentList)));
        contentList.forEach(log::info);
        ChatResponse response = chatModel.call(prompt);
        log.info("Response: {}", response.getResult().getOutput().getContent());
        return new Answer(response.getResult().getOutput().getContent());
    }
}
