package com.dream.backend.service;

import com.dream.backend.domain.Question;

import java.util.List;

public interface QuestionService {
    List<Question> queryQuestionList(Question question);

    int addQuestion(Question question);
}
