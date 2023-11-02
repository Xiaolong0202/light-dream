package com.dream.backend.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dream.backend.domain.Answer;
import com.dream.backend.service.AnswerService;
import com.dream.backend.mapper.AnswerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 13430
* @description 针对表【answer】的数据库操作Service实现
* @createDate 2023-10-31 21:29:39
*/
@Service
public class AnswerServiceImpl implements AnswerService{

    @Autowired
    private AnswerMapper answerMapper;

    public int addAnswer(Answer answer){
        answer.setId(IdUtil.getSnowflakeNextId());
        int answerResult = answerMapper.insert(answer);
        if (answerResult != 0) {
            return 1;
        } else {
            return answerResult;
        }
    }

}




