package org.lemon.bugtrackplatformserver.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 报名申请状态枚举
 */
@Getter
@AllArgsConstructor
public enum ApplicationStatusEnum {
    /**
     * 待审核
     */
    PENDING("pending", "待审核"),

    /**
     * 已通过
     */
    APPROVED("approved", "已通过"),

    /**
     * 未通过
     */
    REJECTED("rejected", "未通过");

    // 状态code
    private final String code;
    // 状态信息
    private final String message;

    /**
     * 根据code获取枚举
     *
     * @param code 状态code
     * @return 对应的枚举值
     */
    public static ApplicationStatusEnum fromCode(String code) {
        for (ApplicationStatusEnum status : ApplicationStatusEnum.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}