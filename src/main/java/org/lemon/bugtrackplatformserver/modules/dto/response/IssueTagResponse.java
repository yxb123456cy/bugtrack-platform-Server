package org.lemon.bugtrackplatformserver.modules.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IssueTagResponse {
    private Long id;
    private Long issueId;
    private Long tagId;
    private LocalDateTime createdAt;
}

