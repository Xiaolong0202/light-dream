package com.dream.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author LiuXiaolong
 * @Description light-dream
 * @DateTime 2023/10/31  16:52
 **/
@RestController
public class HelloController {

    @RequestMapping("hello")
    public String hello(){
        return "hello";
    }
}
