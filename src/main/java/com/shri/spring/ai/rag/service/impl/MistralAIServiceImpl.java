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
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MistralAIServiceImpl implements AIService {

    private final ChatModel chatModel;

    @Override
    public Answer askQuestion(Question question) {
        PromptTemplate promptTemplate = new PromptTemplate(question.question());
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatModel.call(prompt);
        log.info("Response: {}", response.getResult().getOutput().getContent());
        return new Answer(response.getResult().getOutput().getContent());
    }
}
