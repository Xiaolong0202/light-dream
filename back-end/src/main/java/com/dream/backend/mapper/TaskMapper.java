package com.dream.backend.mapper;

import com.dream.backend.domain.Task;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 13430
* @description 针对表【task】的数据库操作Mapper
* @createDate 2023-10-31 21:21:48
* @Entity com.dream.backend.domain.Task
*/
@Mapper
public interface TaskMapper extends BaseMapper<Task> {

}




