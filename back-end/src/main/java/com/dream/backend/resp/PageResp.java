package com.dream.backend.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询列表结果的包装类
 * @Author LiuXiaolong
 * @Description train-12306-system
 * @DateTime 2023/9/23  21:54
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResp<T> implements Serializable {
    private Long total;
    private List<T> list;
}
