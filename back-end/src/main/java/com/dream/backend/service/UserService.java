package com.dream.backend.service;

import com.dream.backend.domain.User;

/**
* @author 13430
* @description 针对表【user】的数据库操作Service
* @createDate 2023-10-31 16:51:47
*/
public interface UserService {

    Integer login(User user);

    void register(User user);

    User queryByPhone(String phone);
}
