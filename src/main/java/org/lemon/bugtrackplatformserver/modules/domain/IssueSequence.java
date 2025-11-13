package org.lemon.bugtrackplatformserver.modules.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目问题编号序列表
 * @TableName issue_sequence
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value ="issue_sequence")
@Data
public class IssueSequence implements Serializable {
    /**
     * 项目ID
     */
    @TableId(value = "project_id")
    private Long projectId;

    /**
     * 下一个可用编号
     */
    @TableField(value = "next_no")
    private Long nextNo;

    /**
     * 更新时间
     */
    @TableField(value = "updated_at")
    private LocalDateTime updatedAt;

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
        IssueSequence other = (IssueSequence) that;
        return (this.getProjectId() == null ? other.getProjectId() == null : this.getProjectId().equals(other.getProjectId()))
            && (this.getNextNo() == null ? other.getNextNo() == null : this.getNextNo().equals(other.getNextNo()))
            && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getProjectId() == null) ? 0 : getProjectId().hashCode());
        result = prime * result + ((getNextNo() == null) ? 0 : getNextNo().hashCode());
        result = prime * result + ((getUpdatedAt() == null) ? 0 : getUpdatedAt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                " [" +
                "Hash = " + hashCode() +
                ", projectId=" + projectId +
                ", nextNo=" + nextNo +
                ", updatedAt=" + updatedAt +
                ", serialVersionUID=" + serialVersionUID +
                "]";
    }
}