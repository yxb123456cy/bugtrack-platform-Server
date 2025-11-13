package org.lemon.bugtrackplatformserver.modules.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationResponse {
    private Long id;
    private Long userId;
    private Long projectId;
    private String type;
    private Long targetId;
    private String content;
    private Integer isRead;
    private LocalDateTime createdAt;
}

