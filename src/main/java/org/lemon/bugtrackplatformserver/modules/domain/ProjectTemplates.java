package org.lemon.bugtrackplatformserver.modules.domain;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * 项目模板表
 * @TableName project_templates
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value ="project_templates")
@Data
public class ProjectTemplates implements Serializable {
    /**
     * 模板ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 所属空间ID
     */
    @TableField(value = "space_id")
    private Long spaceId;

    /**
     * 模板名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 模板描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 是否为默认模板
     */
    @TableField(value = "is_default")
    private Integer isDefault;

    /**
     * 默认配置内容（如状态流、字段模板）
     */
    @TableField(value = "config_json")
    private Object configJson;

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
        ProjectTemplates other = (ProjectTemplates) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSpaceId() == null ? other.getSpaceId() == null : this.getSpaceId().equals(other.getSpaceId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getIsDefault() == null ? other.getIsDefault() == null : this.getIsDefault().equals(other.getIsDefault()))
            && (this.getConfigJson() == null ? other.getConfigJson() == null : this.getConfigJson().equals(other.getConfigJson()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSpaceId() == null) ? 0 : getSpaceId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getIsDefault() == null) ? 0 : getIsDefault().hashCode());
        result = prime * result + ((getConfigJson() == null) ? 0 : getConfigJson().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                " [" +
                "Hash = " + hashCode() +
                ", id=" + id +
                ", spaceId=" + spaceId +
                ", name=" + name +
                ", description=" + description +
                ", isDefault=" + isDefault +
                ", configJson=" + configJson +
                ", createdAt=" + createdAt +
                ", serialVersionUID=" + serialVersionUID +
                "]";
    }
}