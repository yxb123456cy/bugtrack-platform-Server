package org.lemon.bugtrackplatformserver.modules.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SpaceMemberResponse {
    private Long id;
    private Long spaceId;
    private Long userId;
    private String role;
    private LocalDateTime joinedAt;
}

