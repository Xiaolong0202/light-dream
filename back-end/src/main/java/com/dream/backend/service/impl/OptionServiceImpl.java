package com.dream.backend.service.impl;

import com.dream.backend.domain.Option;
import com.dream.backend.mapper.OptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionServiceImpl {

    @Autowired
    private OptionMapper optionMapper;

    public List<Option> getOptionList(Option option){
        List<Option> result = optionMapper.getOptionList(option);
        return result;
    }

    public int addOption(Option option){
        int result = optionMapper.insert(option);
        if (result != 0) {
            return 1;
        } else {
            return result;
        }
    }

}
