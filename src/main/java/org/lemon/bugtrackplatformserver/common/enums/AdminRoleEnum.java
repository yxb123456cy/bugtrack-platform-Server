package org.lemon.bugtrackplatformserver.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 管理员角色枚举
 */
@Getter
@AllArgsConstructor
public enum AdminRoleEnum {
    /**
     * 超级管理员
     */
    SUPER_ADMIN("super_admin", "超级管理员"),

    /**
     * 普通管理员
     */
    ADMIN("admin", "普通管理员");

    // 角色code
    private final String code;
    // 角色描述
    private final String description;

    /**
     * 根据code获取枚举
     *
     * @param code 角色code
     * @return 对应的枚举值
     */
    public static AdminRoleEnum fromCode(String code) {
        for (AdminRoleEnum role : AdminRoleEnum.values()) {
            if (role.getCode().equals(code)) {
                return role;
            }
        }
        return null;
    }
}