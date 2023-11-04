package com.dream.backend.service;

import com.dream.backend.domain.Answer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dream.backend.domain.User;

import java.util.List;

/**
* @author 13430
* @description 针对表【answer】的数据库操作Service
* @createDate 2023-10-31 21:29:39
*/
public interface AnswerService {

    public int addAnswer(Answer answer);

    public List<Answer> queryAnswerList(Answer answer);

    int modifyAnswer(Answer answer);

}
