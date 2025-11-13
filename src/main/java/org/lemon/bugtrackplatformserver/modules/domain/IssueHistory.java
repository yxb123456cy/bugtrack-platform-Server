package org.lemon.bugtrackplatformserver.modules.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 问题单历史记录表
 * @TableName issue_history
 */
@TableName(value ="issue_history")
@Data
public class IssueHistory implements Serializable {
    /**
     * 历史记录ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 问题单ID
     */
    @TableField(value = "issue_id")
    private Long issueId;

    /**
     * 操作人ID
     */
    @TableField(value = "actor_id")
    private Long actorId;

    /**
     * 操作类型：status_change/assign/comment/edit/attach
     */
    @TableField(value = "action")
    private String action;

    /**
     * 变更前值
     */
    @TableField(value = "from_value")
    private String fromValue;

    /**
     * 变更后值
     */
    @TableField(value = "to_value")
    private String toValue;

    /**
     * 备注信息
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 创建时间
     */
    @TableField(value = "created_at")
    private LocalDateTime createdAt;

    @Serial
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
        IssueHistory other = (IssueHistory) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getIssueId() == null ? other.getIssueId() == null : this.getIssueId().equals(other.getIssueId()))
            && (this.getActorId() == null ? other.getActorId() == null : this.getActorId().equals(other.getActorId()))
            && (this.getAction() == null ? other.getAction() == null : this.getAction().equals(other.getAction()))
            && (this.getFromValue() == null ? other.getFromValue() == null : this.getFromValue().equals(other.getFromValue()))
            && (this.getToValue() == null ? other.getToValue() == null : this.getToValue().equals(other.getToValue()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getIssueId() == null) ? 0 : getIssueId().hashCode());
        result = prime * result + ((getActorId() == null) ? 0 : getActorId().hashCode());
        result = prime * result + ((getAction() == null) ? 0 : getAction().hashCode());
        result = prime * result + ((getFromValue() == null) ? 0 : getFromValue().hashCode());
        result = prime * result + ((getToValue() == null) ? 0 : getToValue().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                " [" +
                "Hash = " + hashCode() +
                ", id=" + id +
                ", issueId=" + issueId +
                ", actorId=" + actorId +
                ", action=" + action +
                ", fromValue=" + fromValue +
                ", toValue=" + toValue +
                ", remark=" + remark +
                ", createdAt=" + createdAt +
                ", serialVersionUID=" + serialVersionUID +
                "]";
    }
}