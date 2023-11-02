package com.dream.backend.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dream.backend.domain.Answer;
import com.dream.backend.domain.Task;
import com.dream.backend.mapper.AnswerMapper;
import com.dream.backend.mapper.TaskMapper;
import com.dream.backend.resp.CommonResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    TaskMapper taskMapper;

    /**
     * 查询一个孩子所有的anwser
     * @return
     */
    @GetMapping("/getList/{childId}")
    public CommonResp<?> getAllAnwser(@PathVariable("childId") Long childId){
        LambdaQueryWrapper<Answer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Answer::getId);
        queryWrapper.eq(Answer::getChildUserId,childId);
        List<Answer> answers = answerMapper.selectList(queryWrapper);
        List<Map> res = answers.stream().map(answer -> {
            Map map = BeanUtil.copyProperties(answer, Map.class);
            LambdaQueryWrapper<Task> taskLambdaQueryWrapper = new LambdaQueryWrapper<>();
            taskLambdaQueryWrapper.eq(Task::getId, answer.getTaskId());
            Task task = taskMapper.selectOne(taskLambdaQueryWrapper);
            map.put("task", task);
            return map;
        }).collect(Collectors.toList());
        return CommonResp.buildSuccess(res,"查询成功");
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
