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
 * Token 存储表
 * @TableName auth_tokens
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value ="auth_tokens")
@Data
public class AuthTokens implements Serializable {
    /**
     * Token ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 令牌字符串
     */
    @TableField(value = "token")
    private String token;

    /**
     * 登录设备标识
     */
    @TableField(value = "device")
    private String device;

    /**
     * 过期时间
     */
    @TableField(value = "expired_at")
    private LocalDateTime expiredAt;

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
        AuthTokens other = (AuthTokens) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getToken() == null ? other.getToken() == null : this.getToken().equals(other.getToken()))
            && (this.getDevice() == null ? other.getDevice() == null : this.getDevice().equals(other.getDevice()))
            && (this.getExpiredAt() == null ? other.getExpiredAt() == null : this.getExpiredAt().equals(other.getExpiredAt()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getToken() == null) ? 0 : getToken().hashCode());
        result = prime * result + ((getDevice() == null) ? 0 : getDevice().hashCode());
        result = prime * result + ((getExpiredAt() == null) ? 0 : getExpiredAt().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                " [" +
                "Hash = " + hashCode() +
                ", id=" + id +
                ", userId=" + userId +
                ", token=" + token +
                ", device=" + device +
                ", expiredAt=" + expiredAt +
                ", createdAt=" + createdAt +
                ", serialVersionUID=" + serialVersionUID +
                "]";
    }
}