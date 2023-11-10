package com.dream.backend.enums;

import com.dream.backend.enums.option;
import lombok.Data;

import java.util.List;

@Data
public class problem {
    private String problemName;
    private Integer problemType;
    private List<option> option;
}
