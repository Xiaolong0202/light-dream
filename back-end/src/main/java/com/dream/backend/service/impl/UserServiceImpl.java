package com.dream.backend.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dream.backend.domain.User;
import com.dream.backend.exception.BusinessException;
import com.dream.backend.resp.PageResp;
import com.dream.backend.service.UserService;
import com.dream.backend.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 13430
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-10-31 16:51:47
*/
@Slf4j
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserMapper userMapper;

    @Override
    public Integer login(User user) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getPhone,user.getPhone());
        userLambdaQueryWrapper.eq(User::getPassword,user.getPassword());
        User userDB = userMapper.selectOne(userLambdaQueryWrapper);
        if (userDB == null){
            throw new BusinessException("账号或密码不对");
        }
        return userDB.getUserType();
    }

    @Override
    public void register(User user) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getPhone,user.getPhone());
        User userDB = userMapper.selectOne(userLambdaQueryWrapper);
        if (userDB!=null){
            throw new BusinessException("该手机号已经被注册");
        }
        user.setId(IdUtil.getSnowflakeNextId());
        userMapper.insert(user);
    }

    @Override
    public User queryByPhone(String phone) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getPhone,phone);
        User userDB = userMapper.selectOne(userLambdaQueryWrapper);
        if (userDB==null){
            throw new BusinessException("没有对应的用户");
        }
        return userDB;
    }

    @Override
    public PageResp<User> queryByVolunteerId(Long volunteerId, Integer currentPage, Integer pageSize) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getVolunteerId,volunteerId);
        userLambdaQueryWrapper.eq(User::getUserType,1);//必须为孩子类型
        PageHelper.startPage(currentPage,pageSize);//分页
        List<User> userList = userMapper.selectList(userLambdaQueryWrapper);
        PageInfo<User> userPageInfo = new PageInfo<>(userList);//构建分页PageInfo
        log.info("总页数 " + userPageInfo.getPages());
        log.info("总行数 " + userPageInfo.getTotal());
        return new PageResp<>(userPageInfo.getTotal(),userList);
    }

    @Override
    public PageResp<User> getAllChildren(Integer currentPage, Integer pageSize) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUserType,1);//必须为孩子类型
        PageHelper.startPage(currentPage,pageSize);//分页
        List<User> userList = userMapper.selectList(userLambdaQueryWrapper);

        for(User user:userList){
            if(user.getVolunteerId()==null){
                user.setPassword("无");
            }else{
                User volun = new User();
                volun.setId(user.getVolunteerId());
                List<User> volunteer = userMapper.queryUserList(volun);
                user.setPassword(volunteer.get(0).getName());
            }
        }

        PageInfo<User> userPageInfo = new PageInfo<>(userList);//构建分页PageInfo
        log.info("总页数 " + userPageInfo.getPages());
        log.info("总行数 " + userPageInfo.getTotal());
        return new PageResp<>(userPageInfo.getTotal(),userList);
    }

    @Override
    public void bindChild(Long volunteerId, String childPhone) {
        User entity = new User();
        entity.setVolunteerId(volunteerId);

        LambdaQueryWrapper<User> updateWrapper = new LambdaQueryWrapper<>();
        updateWrapper.eq(User::getPhone,childPhone);
        updateWrapper.eq(User::getUserType,1);//必须为孩子类型

        int update = userMapper.update(entity, updateWrapper);
        if (update<=0){
            //没有做出更改也就是说没有绑定成功
            throw new BusinessException("没有绑定成功,貌似该手机号没有对应的儿童(⊙︿⊙)");
        }
    }

    @Override
    public void disBindChild(Long childId) {
        userMapper.disBindChild(childId);
    }

    @Override
    public List<User> queryUserList(User user){
        List<User> result = userMapper.queryUserList(user);
        return result;
    }

    @Override
    public List<User> queryAllUser(User user){
        List<User> result = userMapper.queryAllUser(user);
        return result;
    }

    @Override
    public int modifyUser(User user){
          int result = userMapper.updateUserSelective(user);
          return  result;
    }

    @Override
    public int addScore(User user) {
        return userMapper.addScore(user);
    }
}




