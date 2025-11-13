package org.lemon.bugtrackplatformserver.modules.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IssueCommentResponse {
    private Long id;
    private Long issueId;
    private Long userId;
    private String content;
    private String mentionedUserIds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

