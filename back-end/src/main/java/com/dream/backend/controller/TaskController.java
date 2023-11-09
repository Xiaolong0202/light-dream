package com.dream.backend.controller;

import com.dream.backend.domain.Answer;
import com.dream.backend.domain.Task;
import com.dream.backend.domain.User;
import com.dream.backend.resp.CommonResp;
import com.dream.backend.service.TaskService;
import com.dream.backend.service.impl.AnswerServiceImpl;
import com.dream.backend.service.impl.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequestMapping("/task")
@RestController
public class TaskController {

    @Autowired
    private TaskServiceImpl taskService;

    @Autowired
    private AnswerServiceImpl answerService;

    @RequestMapping(value = "/queryTaskList", method = RequestMethod.POST,headers = "Accept=application/json")
    public CommonResp<List<Task>> queryTaskList(@RequestBody User user){
        CommonResp<List<Task>> commonResp = new CommonResp<List<Task>>();
        Task task = new Task();
        task.setAdminId(user.getId());
        task.setIsdelete(0);
        try{
            List<Task> hasTask = taskService.queryTaskList(task);
            System.out.println(111);
            System.out.println(hasTask.toString());
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
        try{
            int result = taskService.addTask(task);
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
    public CommonResp<Integer> deleteTaskById(@RequestBody Task task){
        CommonResp<Integer> commonResp = new CommonResp<Integer>();
        try {
            int result = taskService.deleteTaskById(task);
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

    @RequestMapping(value = "/releaseTask", method = RequestMethod.POST,headers = "Accept=application/json")
    public CommonResp<Integer> releaseTask(@RequestBody Map<String, Object> data){

        Task task = new Task();
        task.setId(Long.parseLong((String) data.get("id")));

        List<User> users = new ArrayList<User>();
        String userList = data.get("users").toString();
        String[] userIds = userList.split(",");
        for(String userId:userIds){
            User user = new User();
            user.setId(Long.parseLong(userId));
            users.add(user);
        }

        int result = 1;

        for(User user : users){
            Answer answer = new Answer();
            answer.setTaskId(task.getId());
            answer.setChildUserId(user.getId());
            answer.setAnswerStatus(1);
            if(answerService.addAnswer(answer)==0){
                result = 0;
            }
        }

        CommonResp<Integer> commonResp = new CommonResp<Integer>();

        try {
            if (result != 0){
                commonResp.setSuccess(true);
                commonResp.setContent(1);
                commonResp.setMessage("发布成功");
            }else {
                commonResp.setSuccess(false);
                commonResp.setContent(0);
                commonResp.setMessage("发布失败");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return commonResp;

    }

    @RequestMapping(value = "/getTaskByAnswer", method = RequestMethod.POST,headers = "Accept=application/json")
    public CommonResp<List<Task>> getTaskByAnswer(@RequestBody Answer answer){
        Task task = new Task();
        task.setId(answer.getTaskId());
        CommonResp<List<Task>> commonResp = new CommonResp<List<Task>>();
        try{
            List<Task> hasTask = taskService.queryTaskList(task);
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



}
