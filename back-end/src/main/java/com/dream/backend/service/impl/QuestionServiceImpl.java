package com.dream.backend.service.impl;

import com.dream.backend.domain.Question;
import com.dream.backend.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl {

    @Autowired
    QuestionMapper questionMapper;

    public List<Question> queryQuestionList(Question question){
        List<Question> result = questionMapper.queryQuestionList(question);
        return result;
    }

    public int addQuestion(Question question){
        int result = questionMapper.insertQuestion(question);
        if (result != 0) {
            return 1;
        } else {
            return result;
        }
    }

}
