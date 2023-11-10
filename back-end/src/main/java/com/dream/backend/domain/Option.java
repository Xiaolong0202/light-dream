package com.dream.backend.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Objects;

@TableName(value ="options")
@Data
public class Option {

    private Integer id;

    private String optionContent;

    private Integer questionId;

    private Long taskId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Option option = (Option) o;
        return Objects.equals(id, option.id) && Objects.equals(optionContent, option.optionContent) && Objects.equals(questionId, option.questionId) && Objects.equals(taskId, option.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, optionContent, questionId, taskId);
    }

    @Override
    public String toString() {
        return "Option{" +
                "id=" + id +
                ", optionContent='" + optionContent + '\'' +
                ", questionId=" + questionId +
                ", taskId=" + taskId +
                '}';
    }
}
