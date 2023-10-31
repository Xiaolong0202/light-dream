package com.dream.backend.controller;

import com.dream.backend.domain.User;
import com.dream.backend.resp.CommonResp;
import com.dream.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author LiuXiaolong
 * @Description light-dream
 * @DateTime 2023/10/31  16:56
 **/
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    UserService userService;


    /**
     * 使用 phone 与 password进行登录
     * @param user
     * @return
     */
    @PostMapping("/login")
    public CommonResp<?> login(@RequestBody User user){
        userService.login(user);
        return CommonResp.buildSuccess("登录成功");
    }

    @PostMapping("/register")
    public CommonResp<?> register(@RequestBody User user){
       userService.register(user);
        return CommonResp.buildSuccess("注册成功");
    }



}
