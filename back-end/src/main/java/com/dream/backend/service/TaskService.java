package com.dream.backend.service;

import com.dream.backend.domain.Task;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 13430
* @description 针对表【task】的数据库操作Service
* @createDate 2023-10-31 21:21:48
*/
public interface TaskService{

    public List<Task> queryTaskList(Task task);

    public int addTask(Task task);

    public int deleteTaskById(Task task);

}
