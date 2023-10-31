package com.dream.backend.enums;

import lombok.Data;

/**
 *
 * 表示 用户类型的枚举
 *
 * @Author LiuXiaolong
 * @Description light-dream
 * @DateTime 2023/10/31  17:28
 **/

public enum UserTypeEnum {

    CHILD(1,"儿童"),VOLUNTEER(2,"志愿者");

    private Integer code;

    private String description;

    UserTypeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
}
