package org.lemon.bugtrackplatformserver.modules.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SettingResponse {
    private Long id;
    private Long projectId;
    private String key;
    private String value;
    private LocalDateTime updatedAt;
}

