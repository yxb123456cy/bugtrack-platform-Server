package org.lemon.bugtrackplatformserver.modules.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IssueResponse {
    private Long id;
    private Long projectId;
    private Long issueNo;
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

