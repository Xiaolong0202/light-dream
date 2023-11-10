package com.dream.backend.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Objects;

@TableName(value ="question")
@Data
public class Question {

    private Integer id;

    private String title;

    private Integer type;

    private Long taskId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(id, question.id) && Objects.equals(title, question.title) && Objects.equals(type, question.type) && Objects.equals(taskId, question.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, type, taskId);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", taskId=" + taskId +
                '}';
    }
}
