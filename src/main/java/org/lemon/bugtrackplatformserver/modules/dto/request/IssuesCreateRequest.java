package org.lemon.bugtrackplatformserver.modules.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IssuesCreateRequest {
    @NotNull
    private Long projectId;
    @NotBlank
    private String type;
    @NotBlank
    private String title;
    private String description;
    private String severity;
    private String priority;
    @NotNull
    private Long reporterId;
    private Long assigneeId;
    private String module;
}

