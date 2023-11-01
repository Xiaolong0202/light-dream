package com.dream.backend.mapper;

import com.dream.backend.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
* @author 13430
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-10-31 16:51:47
* @Entity com.dream.backend.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 给孩子的志愿者解绑
     * @param childId
     */
    @Update("update dreams.user set volunteer_id = null where id = #{childId}")
    void disBindChild(@Param("childId") Long childId);
}




