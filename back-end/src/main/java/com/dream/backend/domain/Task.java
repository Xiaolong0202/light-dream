package com.dream.backend.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * 
 * @TableName task
 */
@TableName(value ="task")
@Data
public class Task implements Serializable {
    /**
     * 主键
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 任务名
     */
    private String name;

    /**
     * 管理者者ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long adminId;

    /**
     * 任务描述
     */
    private String description;

    /**
     * 任务总分
     */
    private Integer totalScore;

    /**
     * 任务难度
     */
    private String difficulty;

    /**
     * 任务优先级
     */
    private String priority;

    /**
     * 任务预算
     */
    private Integer budget;

    /**
     * 任务开始时间
     */
    private Date startTime;

    /**
     * 任务结束时间
     */
    private Date endTime;

    /**
     * 任务提交形式
     */
    private Integer submitForm;

    /**
     * 任务类型
     */
    private Integer type;

    /**
     * 任务删除属性
     */
    private Integer isdelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Task other = (Task) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getAdminId() == null ? other.getAdminId() == null : this.getAdminId().equals(other.getAdminId()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getTotalScore() == null ? other.getTotalScore() == null : this.getTotalScore().equals(other.getTotalScore()))
            && (this.getDifficulty() == null ? other.getDifficulty() == null : this.getDifficulty().equals(other.getDifficulty()))
            && (this.getPriority() == null ? other.getPriority() == null : this.getPriority().equals(other.getPriority()))
            && (this.getBudget() == null ? other.getBudget() == null : this.getBudget().equals(other.getBudget()))
            && (this.getStartTime() == null ? other.getStartTime() == null : this.getStartTime().equals(other.getStartTime()))
            && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
            && (this.getSubmitForm() == null ? other.getSubmitForm() == null : this.getSubmitForm().equals(other.getSubmitForm()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getIsdelete() == null ? other.getIsdelete() == null : this.getIsdelete().equals(other.getIsdelete()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getAdminId() == null) ? 0 : getAdminId().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getTotalScore() == null) ? 0 : getTotalScore().hashCode());
        result = prime * result + ((getDifficulty() == null) ? 0 : getDifficulty().hashCode());
        result = prime * result + ((getPriority() == null) ? 0 : getPriority().hashCode());
        result = prime * result + ((getBudget() == null) ? 0 : getBudget().hashCode());
        result = prime * result + ((getStartTime() == null) ? 0 : getStartTime().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        result = prime * result + ((getSubmitForm() == null) ? 0 : getSubmitForm().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getIsdelete() == null) ? 0 : getIsdelete().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", adminId=").append(adminId);
        sb.append(", description=").append(description);
        sb.append(", totalScore=").append(totalScore);
        sb.append(", difficulty=").append(difficulty);
        sb.append(", priority=").append(priority);
        sb.append(", budget=").append(budget);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", submitForm=").append(submitForm);
        sb.append(", type=").append(type);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }


}