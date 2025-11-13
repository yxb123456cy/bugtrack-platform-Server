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
 * 附件表
 * @TableName issue_attachments
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value ="issue_attachments")
@Data
public class IssueAttachments implements Serializable {
    /**
     * 附件ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 所属问题单ID
     */
    @TableField(value = "issue_id")
    private Long issueId;

    /**
     * 上传者ID
     */
    @TableField(value = "uploader_id")
    private Long uploaderId;

    /**
     * 文件名
     */
    @TableField(value = "file_name")
    private String fileName;

    /**
     * 文件大小（字节）
     */
    @TableField(value = "file_size")
    private Long fileSize;

    /**
     * MinIO 对象路径或本地路径
     */
    @TableField(value = "storage_path")
    private String storagePath;

    /**
     * 文件类型
     */
    @TableField(value = "mime_type")
    private String mimeType;

    /**
     * 上传时间
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
        IssueAttachments other = (IssueAttachments) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getIssueId() == null ? other.getIssueId() == null : this.getIssueId().equals(other.getIssueId()))
            && (this.getUploaderId() == null ? other.getUploaderId() == null : this.getUploaderId().equals(other.getUploaderId()))
            && (this.getFileName() == null ? other.getFileName() == null : this.getFileName().equals(other.getFileName()))
            && (this.getFileSize() == null ? other.getFileSize() == null : this.getFileSize().equals(other.getFileSize()))
            && (this.getStoragePath() == null ? other.getStoragePath() == null : this.getStoragePath().equals(other.getStoragePath()))
            && (this.getMimeType() == null ? other.getMimeType() == null : this.getMimeType().equals(other.getMimeType()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getIssueId() == null) ? 0 : getIssueId().hashCode());
        result = prime * result + ((getUploaderId() == null) ? 0 : getUploaderId().hashCode());
        result = prime * result + ((getFileName() == null) ? 0 : getFileName().hashCode());
        result = prime * result + ((getFileSize() == null) ? 0 : getFileSize().hashCode());
        result = prime * result + ((getStoragePath() == null) ? 0 : getStoragePath().hashCode());
        result = prime * result + ((getMimeType() == null) ? 0 : getMimeType().hashCode());
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
                ", uploaderId=" + uploaderId +
                ", fileName=" + fileName +
                ", fileSize=" + fileSize +
                ", storagePath=" + storagePath +
                ", mimeType=" + mimeType +
                ", createdAt=" + createdAt +
                ", serialVersionUID=" + serialVersionUID +
                "]";
    }
}