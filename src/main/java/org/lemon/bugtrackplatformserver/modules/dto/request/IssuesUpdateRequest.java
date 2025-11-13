package org.lemon.bugtrackplatformserver.modules.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IssuesUpdateRequest {
    @NotNull
    private Long id;
    private Long projectId;
    private String type;
    private String title;
    private String description;
    private String status;
    private String severity;
    private String priority;
    private Long reporterId;
    private Long assigneeId;
    private String module;
    private String resolution;
}

