package org.lemon.bugtrackplatformserver.modules.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjectMemberResponse {
    private Long id;
    private Long projectId;
    private Long userId;
    private String role;
    private LocalDateTime joinedAt;
}

