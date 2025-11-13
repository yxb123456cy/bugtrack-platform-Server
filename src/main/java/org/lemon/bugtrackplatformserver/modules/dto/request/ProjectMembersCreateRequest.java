package org.lemon.bugtrackplatformserver.modules.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProjectMembersCreateRequest {
    @NotNull
    private Long projectId;
    @NotNull
    private Long userId;
    @NotBlank
    private String role;
}

