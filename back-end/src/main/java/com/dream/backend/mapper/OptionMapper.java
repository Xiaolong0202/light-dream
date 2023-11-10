package com.dream.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dream.backend.domain.Option;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OptionMapper extends BaseMapper<Option> {

    List<Option> getOptionList(Option option);

    int insert(Option option);

}
