package org.lemon.bugtrackplatformserver.modules.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IssueTagsBindRequest {
    @NotNull
    private Long issueId;
    @NotNull
    private Long tagId;
}

