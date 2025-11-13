package org.lemon.bugtrackplatformserver.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventStatusEnum {
    draft("draft", "草稿"),
    active("active", "进行中"),
    closed("closed", "已结束"),
    archived("archived", "已归档"),
    ;

    // 状态code
    private final String code;
    // 状态信息
    private final String message;
}
