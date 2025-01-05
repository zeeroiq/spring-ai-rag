package com.shri.spring.ai.rag.controller;

import com.shri.spring.ai.rag.model.Answer;
import com.shri.spring.ai.rag.model.Question;
import com.shri.spring.ai.rag.service.AIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class QuestionController {

    private final AIService mistralAIService;

    @PostMapping("/ask")
    public Answer askQuestion(@RequestBody Question question) {
        return mistralAIService.askQuestion(question);
    }
}
