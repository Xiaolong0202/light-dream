package com.dream.backend.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dream.backend.domain.Answer;
import com.dream.backend.domain.Task;
import com.dream.backend.domain.User;
import com.dream.backend.mapper.AnswerMapper;
import com.dream.backend.mapper.TaskMapper;
import com.dream.backend.resp.AnwserResp;
import com.dream.backend.resp.CommonResp;
import com.dream.backend.service.impl.AnswerServiceImpl;
import com.dream.backend.service.impl.TaskServiceImpl;
import com.dream.backend.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private TaskServiceImpl taskService;

    @Autowired
    private AnswerServiceImpl answerService;

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
        List<AnwserResp> res = answers.stream().map(answer -> {
            AnwserResp anwserResp = BeanUtil.copyProperties(answer, AnwserResp.class);
            LambdaQueryWrapper<Task> taskLambdaQueryWrapper = new LambdaQueryWrapper<>();
            taskLambdaQueryWrapper.eq(Task::getId, answer.getTaskId());
            Task task = taskMapper.selectOne(taskLambdaQueryWrapper);
            anwserResp.setTask(task);
            return anwserResp;
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
    @PutMapping("/child-submit")
    public CommonResp<?> childSubmit(@RequestBody Answer answer){
        answer.setAnswerStatus(2);
        answerMapper.updateById(answer);
        return CommonResp.buildSuccess("任务提交成功");
    }

    @RequestMapping(value = "/queryAnswerListByVolunteerId", method = RequestMethod.POST,headers = "Accept=application/json")
    public CommonResp<List<Answer>> queryAnswerListByVolunteerId(@RequestBody User user){
        List<User> children = userService.queryUserList(user);
        List<Answer> answers = new ArrayList<Answer>();
        for(User child:children){
            Answer answer = new Answer();
            answer.setChildUserId(child.getId());
            answer.setAnswerStatus(1);
            answers.addAll(answerService.queryAnswerList(answer));
        }

        CommonResp<List<Answer>> commonResp = new CommonResp<List<Answer>>();
        try {
            if (CollectionUtils.isEmpty(answers)){
                commonResp.setSuccess(false);
                commonResp.setContent(null);
                commonResp.setMessage("失败");
            }else {
                commonResp.setSuccess(true);
                commonResp.setContent(answers);
                commonResp.setMessage("成功");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return commonResp;

    }



}
