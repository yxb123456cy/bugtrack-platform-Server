package org.lemon.bugtrackplatformserver.modules.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IssueCommentCreateRequest {
    @NotNull
    private Long issueId;
    @NotNull
    private Long userId;
    @NotBlank
    private String content;
    private String mentionedUserIds;
}

