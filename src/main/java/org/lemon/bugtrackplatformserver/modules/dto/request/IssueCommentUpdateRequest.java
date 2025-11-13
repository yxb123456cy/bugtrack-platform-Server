package org.lemon.bugtrackplatformserver.modules.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IssueCommentUpdateRequest {
    @NotNull
    private Long id;
    private String content;
    private String mentionedUserIds;
}

