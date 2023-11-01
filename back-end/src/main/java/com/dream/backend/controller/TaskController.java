package com.dream.backend.controller;

import com.dream.backend.domain.Task;
import com.dream.backend.domain.User;
import com.dream.backend.resp.CommonResp;
import com.dream.backend.service.TaskService;
import com.dream.backend.service.impl.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
@RequestMapping("/task")
@RestController
public class TaskController {

    @Autowired
    private TaskServiceImpl taskServiceImpl;

    @RequestMapping(value = "/queryTaskList", method = RequestMethod.POST,headers = "Accept=application/json")
    public CommonResp<List<Task>> queryTaskList(@RequestBody User user){
        System.out.println(user.toString());
        CommonResp<List<Task>> commonResp = new CommonResp<List<Task>>();
        Task task = new Task();
        task.setVolunteerId(user.getVolunteerId());
        try{
            List<Task> hasTask = taskServiceImpl.queryTaskList(task);
            if (CollectionUtils.isEmpty(hasTask)) {
                commonResp.setSuccess(false);
                commonResp.setContent(null);
                commonResp.setMessage("失败");
            }else{
                commonResp.setSuccess(true);
                commonResp.setContent(hasTask);
                commonResp.setMessage("成功");
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return commonResp;
    }

    @RequestMapping(value = "/addTask", method = RequestMethod.POST,headers = "Accept=application/json")
    public CommonResp<Integer> addTask(@RequestBody Task task){
        CommonResp<Integer> commonResp = new CommonResp<Integer>();
        System.out.print(task);
        try{
            int result = taskServiceImpl.addTask(task);
            if (result != 0) {
                commonResp.setSuccess(true);
                commonResp.setContent(1);
                commonResp.setMessage("添加成功");
            }else{
                commonResp.setSuccess(false);
                commonResp.setContent(0);
                commonResp.setMessage("添加失败");
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return commonResp;
    }

    @RequestMapping(value = "/deleteTaskById", method = RequestMethod.POST,headers = "Accept=application/json")
    public CommonResp<Integer> deleteProjectInfo(@RequestBody Task task){
        CommonResp<Integer> commonResp = new CommonResp<Integer>();
        try {
            int result = taskServiceImpl.deleteTaskById(task);
            if (result != 0){
                commonResp.setSuccess(true);
                commonResp.setContent(1);
                commonResp.setMessage("删除成功");
            }else {
                commonResp.setSuccess(false);
                commonResp.setContent(0);
                commonResp.setMessage("删除失败");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return commonResp;
    }

}
