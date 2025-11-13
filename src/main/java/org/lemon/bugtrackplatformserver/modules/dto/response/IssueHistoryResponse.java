package org.lemon.bugtrackplatformserver.modules.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IssueHistoryResponse {
    private Long id;
    private Long issueId;
    private Long actorId;
    private String action;
    private String fromValue;
    private String toValue;
    private String remark;
    private LocalDateTime createdAt;
}

