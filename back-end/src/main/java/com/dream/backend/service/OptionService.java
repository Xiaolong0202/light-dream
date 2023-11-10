package com.dream.backend.service;

import com.dream.backend.domain.Option;

import java.util.List;

public interface OptionService {
    List<Option> getOptionList(Option option);

    int addOption(Option option);
}
