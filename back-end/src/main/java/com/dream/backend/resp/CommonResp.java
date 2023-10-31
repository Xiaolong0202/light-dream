package com.dream.backend.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author LiuXiaolong
 * @Description train-12306-system
 * @DateTime 2023/9/19  22:26
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResp<T>{
    private boolean success;
    private String message;
    private T content;

    public CommonResp(T content) {
        this.content = content;
        this.success = true;
        this.message = "请求成功";
    }



    public static <E> CommonResp<E> buildSuccess(E content, String message){
        CommonResp commonResp = new CommonResp();
        commonResp.setMessage(message);
        commonResp.setContent(content);
        commonResp.setSuccess(true);
        return commonResp;
    }
    public static <E> CommonResp<E> buildSuccess(String message) {
        return buildSuccess(null, message);
    }

    public static <E> CommonResp<E> buildFailure(E content,String message){
        CommonResp commonResp = new CommonResp();
        commonResp.setMessage(message);
        commonResp.setContent(content);
        commonResp.setSuccess(false);
        return commonResp;
    }

    public static <E> CommonResp<E> buildFailure(String message){
       return CommonResp.buildFailure(null,message);
    }
}
