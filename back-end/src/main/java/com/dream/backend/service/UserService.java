package com.dream.backend.service;

import com.dream.backend.domain.User;
import com.dream.backend.resp.PageResp;
import java.util.List;

/**
* @author 13430
* @description 针对表【user】的数据库操作Service
* @createDate 2023-10-31 16:51:47
*/
public interface UserService {

    Integer login(User user);

    void register(User user);

    User queryByPhone(String phone);

    PageResp<User> queryByVolunteerId(Long volunteerId, Integer currentPage, Integer pageSize);

    PageResp<User> getAllChildren(Integer currentPage, Integer pageSize);

    void bindChild(Long volunteerId, String childPhone);

    void disBindChild(Long childId);

    List<User> queryUserList(User user);

    List<User> queryAllUser(User user);

    int modifyUser(User user);

    int addScore(User user);

    List<User> queryAllChildren();
}
