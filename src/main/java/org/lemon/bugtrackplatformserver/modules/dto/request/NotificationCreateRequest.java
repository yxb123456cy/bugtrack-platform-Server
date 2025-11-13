package org.lemon.bugtrackplatformserver.modules.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotificationCreateRequest {
    @NotNull
    private Long userId;
    private Long projectId;
    @NotBlank
    private String type;
    private Long targetId;
    @NotBlank
    private String content;
}

