package com.dream.backend.controller;

import com.dream.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author LiuXiaolong
 * @Description light-dream
 * @DateTime 2023/10/31  16:56
 **/

@RestController
public class UserController {

    @Autowired
    UserService userService;

}
