package org.lemon.bugtrackplatformserver.modules.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String displayName;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

