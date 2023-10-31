package com.dream.backend.exception;

import com.dream.backend.resp.CommonResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author LiuXiaolong
 * @Description light-dream
 * @DateTime 2023/10/31  17:10
 **/
@RestController
@ControllerAdvice
@Slf4j
public class GloabalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public CommonResp<?> handleBusinessExc(BusinessException e) {
        e.printStackTrace();
        log.error("出现业务异常{}", e.getErrorMsg());
        return CommonResp.buildFailure(e.getErrorMsg());
    }

    @ExceptionHandler(Exception.class)
    public CommonResp<?> handleBusinessExc(Exception e) {
        e.printStackTrace();
        log.error("出现异常{}", e.toString());
        return CommonResp.buildFailure(e.getMessage());
    }


}
