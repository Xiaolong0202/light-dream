package com.dream.backend.mapper;

import com.dream.backend.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 13430
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-10-31 16:51:47
* @Entity com.dream.backend.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




