package com.dream.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dream.backend.domain.Task;
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

    public List<Task> queryTaskList(Task task){
        List<Task> result = taskMapper.queryTaskList(task);
        return result;
    }

}




