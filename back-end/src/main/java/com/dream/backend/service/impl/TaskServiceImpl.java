package com.dream.backend.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dream.backend.domain.Option;
import com.dream.backend.domain.Question;
import com.dream.backend.domain.Task;
import com.dream.backend.enums.problem;
import com.dream.backend.mapper.OptionMapper;
import com.dream.backend.mapper.QuestionMapper;
import com.dream.backend.service.TaskService;
import com.dream.backend.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 13430
* @description 针对表【task】的数据库操作Service实现
* @createDate 2023-10-31 21:21:48
*/
@Service
public class TaskServiceImpl implements TaskService{

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private OptionMapper optionMapper;

    public List<Task> queryTaskList(Task task){
        List<Task> result = taskMapper.queryTaskList(task);
        return result;
    }

    public int addTask(Task task) {
        if(task.getDifficulty().equals("1")){
            task.setDifficulty("简单");
        }else if(task.getDifficulty().equals("2")){
            task.setDifficulty("普通");
        }else if(task.getDifficulty().equals("3")){
            task.setDifficulty("困难");
        }
        task.setId(IdUtil.getSnowflakeNextId());
        task.setIsdelete(0);
        int taskResult = taskMapper.insert(task);
        if (taskResult != 0) {
            return 1;
        } else {
            return taskResult;
        }
    }

    public int addQuestionnaireTask(Task task,List<problem> problems){
        if(task.getDifficulty().equals("1")){
            task.setDifficulty("简单");
        }else if(task.getDifficulty().equals("2")){
            task.setDifficulty("普通");
        }else if(task.getDifficulty().equals("3")){
            task.setDifficulty("困难");
        }
        long taskId = IdUtil.getSnowflakeNextId();
        task.setId(taskId);
        task.setIsdelete(0);
        int taskResult = taskMapper.insert(task);
        for(int i = 0; i<problems.size(); i++){
            Question question = new Question();
            question.setId(i);
            question.setTitle(problems.get(i).getProblemName());
            question.setType(problems.get(i).getProblemType());
            question.setTaskId(taskId);
            int questionResult = questionMapper.insertQuestion(question);
            if(questionResult == 0){
                taskResult = 0;
            }
            for(int j = 0; j<problems.get(i).getOption().size(); j++){
                Option option = new Option();
                option.setId(j);
                option.setQuestionId(i);
                option.setOptionContent(problems.get(i).getOption().get(j).getChooseTerm());
                option.setTaskId(taskId);
                int optionResult = optionMapper.insert(option);
                if(optionResult == 0){
                    taskResult = 0;
                }
            }
        }
        if (taskResult != 0) {
            return 1;
        } else {
            return taskResult;
        }
    }

    public int deleteTaskById(Task task){
        int taskResult = taskMapper.deleteTaskById(task);
        return taskResult;
    }

}




