package com.dream.backend.exception;

/**
 * @Author LiuXiaolong
 * @Description light-dream
 * @DateTime 2023/10/31  17:09
 **/
public class BusinessException  extends RuntimeException{

    private String errorMsg;

    public BusinessException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }


}
