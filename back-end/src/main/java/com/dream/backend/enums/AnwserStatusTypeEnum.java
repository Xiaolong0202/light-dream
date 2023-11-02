package com.dream.backend.enums;

public enum AnwserStatusTypeEnum {

    NO_ANSWERED(1,"未回答"),ANSWERED(2,"已回答"),APPROVED(3,"已审批"),;

    private Integer code;

    private String description;

    AnwserStatusTypeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
}