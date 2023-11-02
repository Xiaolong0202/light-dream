package com.dream.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dream.backend.domain.Answer;
import com.dream.backend.domain.Task;
import com.dream.backend.mapper.AnswerMapper;
import com.dream.backend.mapper.TaskMapper;
import com.dream.backend.resp.CommonResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author LiuXiaolong
 * @Description light-dream
 * @DateTime 2023/11/2  9:24
 **/

@RequestMapping("/answer")
@RestController
public class ChildAnwserController {


    @Autowired
    AnswerMapper answerMapper;


    /**
     * 查询一个孩子所有的anwser
     * @return
     */
    @GetMapping("/getList/{childId}")
    public CommonResp<?> getAllAnwser(@PathVariable("childId") Long childId){
        LambdaQueryWrapper<Answer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Answer::getChildUserId,childId);
        List<Answer> answers = answerMapper.selectList(queryWrapper);
        return CommonResp.buildSuccess(answers,"查询成功");
    }

    /**
     * 编辑作答
     */
    @PutMapping("/edit")
    public CommonResp<?> edit(@RequestBody Answer answer){
        answerMapper.updateById(answer);
        return CommonResp.buildSuccess("作答成功");
    }

    /**
     * 儿童提交答案，将答案状态更改为2，已提交
     * @param answer
     * @return
     */
    @PutMapping("/child-subsmit")
    public CommonResp<?> childSubmit(@RequestBody Answer answer){
        answer.setAnswerStatus(2);
        answerMapper.updateById(answer);
        return CommonResp.buildSuccess("任务提交成功");
    }

}
