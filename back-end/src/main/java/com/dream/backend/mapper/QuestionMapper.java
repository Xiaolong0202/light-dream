package com.dream.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dream.backend.domain.Question;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

    List<Question> queryQuestionList(Question question);

    int insertQuestion(Question question);

}
