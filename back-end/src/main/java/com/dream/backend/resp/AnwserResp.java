package com.dream.backend.resp;

import com.baomidou.mybatisplus.annotation.TableId;
import com.dream.backend.domain.Task;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @Author LiuXiaolong
 * @Description light-dream
 * @DateTime 2023/11/3  19:41
 **/
@Data
public class AnwserResp {
    /**
     * 主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 儿童用户ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long childUserId;

    /**
     * 任务ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long taskId;

    /**
     * 回答状态 (枚举类型暂时还没有确定)
     */
    private Integer answerStatus;

    /**
     * 回答内容
     */
    private String answerContent;

    /**
     * 图片URL地址
     */
    private String imageUrl;

    /**
     * 分数
     */
    private Integer score;

    /**
     * 评语
     */
    private String comments;

    private Task task;
}
