package org.lemon.bugtrackplatformserver.modules.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IssueTagsUnbindRequest {
    @NotNull
    private Long id;
}

