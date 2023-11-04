package com.dream.backend.controller;

import com.dream.backend.domain.Task;
import com.dream.backend.domain.User;
import com.dream.backend.resp.CommonResp;
import com.dream.backend.resp.PageResp;
import com.dream.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

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
     *
     * @param user
     * @return
     */
    @PostMapping("/login")
    public CommonResp<?> login(@RequestBody User user) {
        Integer userType = userService.login(user);
        return CommonResp.buildSuccess(userType, "登录成功");
    }

    @PostMapping("/register")
    public CommonResp<?> register(@RequestBody User user) {
        userService.register(user);
        return CommonResp.buildSuccess("注册成功");
    }

    /**
     * 根据电话查找用户信息
     */
    @GetMapping("/queryByPhone/{phone}")
    public CommonResp<?> queryByPhone(@PathVariable("phone") String phone) {
        User user = userService.queryByPhone(phone);
        return CommonResp.buildSuccess(user, "查找成功");
    }

    /**
     * 根据志愿者ID获取所有它管理的孩子
     */
    @GetMapping("/queryChildren/{volunteerId}")
    public CommonResp<?> queryChildren(@PathVariable("volunteerId") Long volunteerId, @PathParam("currentPage") Integer currentPage, @PathParam("pageSize") Integer pageSize) {
        PageResp<User> children = userService.queryByVolunteerId(volunteerId,currentPage,pageSize);
        return CommonResp.buildSuccess(children,"查询成功");
    }

    /**
     * 获取所有孩子的列表
     */
    @GetMapping("/getAllChildren")
    public CommonResp<?> getAllChildren(@PathParam("currentPage") Integer currentPage, @PathParam("pageSize") Integer pageSize) {
        PageResp<User> chilren = userService.getAllChildren(currentPage,pageSize);
        return CommonResp.buildSuccess(chilren,"获取列表成功");
    }

    /**
     * 给孩子绑定志愿者！
     * @param volunteerId
     * @param childPhone
     * @return
     */
    @PutMapping("/bindChild")
    public CommonResp<?> bindChild(@PathParam("volunteerId") Long volunteerId,@PathParam("childPhone") String childPhone){
        userService.bindChild(volunteerId,childPhone);
        return CommonResp.buildSuccess("绑定成功");
    }

    /**
     * 解绑指定儿童的志愿者ID
     * @param childId
     * @return
     */
    @PutMapping("/disBindChild")
    public CommonResp<?> bindChild(@PathParam("childId") Long childId){
        userService.disBindChild(childId);
        return CommonResp.buildSuccess("解绑成功");
    }

    @RequestMapping(value = "/queryAllUser", method = RequestMethod.POST,headers = "Accept=application/json")
    public CommonResp<List<User>> queryAllUser(){
        User user = new User();
        CommonResp<List<User>> commonResp = new CommonResp<List<User>>();
        try{
            List<User> hasUser = userService.queryAllUser(user);
            if (CollectionUtils.isEmpty(hasUser)) {
                commonResp.setSuccess(false);
                commonResp.setContent(null);
                commonResp.setMessage("失败");
            }else{
                commonResp.setSuccess(true);
                commonResp.setContent(hasUser);
                commonResp.setMessage("成功");
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return commonResp;
    }

    @RequestMapping(value = "/modifyUser", method = RequestMethod.POST,headers = "Accept=application/json")
    public CommonResp<Integer> modifyUser(@RequestBody User user){
        CommonResp<Integer> commonResp = new CommonResp<Integer>();
        try{
            int result = userService.modifyUser(user);
            if (result != 0){
                commonResp.setSuccess(true);
                commonResp.setContent(result);
                commonResp.setMessage("修改成功");
            }else {
                commonResp.setSuccess(false);
                commonResp.setContent(0);
                commonResp.setMessage("修改失败");
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return commonResp;
    }


}
