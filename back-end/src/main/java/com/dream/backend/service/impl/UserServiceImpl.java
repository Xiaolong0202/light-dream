package com.dream.backend.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dream.backend.domain.User;
import com.dream.backend.exception.BusinessException;
import com.dream.backend.service.UserService;
import com.dream.backend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
* @author 13430
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-10-31 16:51:47
*/
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserMapper userMapper;

    @Override
    public void login(User user) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getPhone,user.getPhone());
        userLambdaQueryWrapper.eq(User::getPassword,user.getPassword());
        User userDB = userMapper.selectOne(userLambdaQueryWrapper);
        if (userDB == null){
            throw new BusinessException("账号或密码不对");
        }
    }

    @Override
    public void register(User user) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getPhone,user.getPhone());
        User userDB = userMapper.selectOne(userLambdaQueryWrapper);
        if (userDB!=null){
            throw new BusinessException("用户已经存在");
        }
        user.setId(IdUtil.getSnowflakeNextId());
        userMapper.insert(user);
    }
}




