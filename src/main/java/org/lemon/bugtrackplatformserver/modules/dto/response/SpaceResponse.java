package org.lemon.bugtrackplatformserver.modules.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SpaceResponse {
    private Long id;
    private String name;
    private String key;
    private Long ownerId;
    private String description;
    private Integer visibility;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

