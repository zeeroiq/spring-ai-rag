package com.shri.spring.ai.rag.service;

import com.shri.spring.ai.rag.model.Answer;
import com.shri.spring.ai.rag.model.Question;

public interface AIService {
    Answer askQuestion(Question question);
}
