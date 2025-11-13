package org.lemon.bugtrackplatformserver.modules.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotificationUpdateRequest {
    @NotNull
    private Long id;
    private Integer isRead;
    private String content;
}

