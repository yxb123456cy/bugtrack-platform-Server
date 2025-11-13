package org.lemon.bugtrackplatformserver.modules.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjectResponse {
    private Long id;
    private Long spaceId;
    private String key;
    private String name;
    private String description;
    private Long ownerId;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

