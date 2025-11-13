package org.lemon.bugtrackplatformserver.modules.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TagResponse {
    private Long id;
    private Long projectId;
    private String name;
    private String color;
    private LocalDateTime createdAt;
}

