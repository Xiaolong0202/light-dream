package com.dream.backend.mapper;

import com.dream.backend.domain.Answer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 13430
* @description 针对表【answer】的数据库操作Mapper
* @createDate 2023-10-31 21:29:39
* @Entity com.dream.backend.domain.Answer
*/
@Mapper
public interface AnswerMapper extends BaseMapper<Answer> {

    int insert(Answer answer);

    List<Answer> queryAnswerList(Answer answer);

    int updateAnswerSelective(Answer answer);

}




