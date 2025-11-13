package org.lemon.bugtrackplatformserver.modules.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IssueHistoryCreateRequest {
    @NotNull
    private Long issueId;
    @NotNull
    private Long actorId;
    @NotBlank
    private String action;
    private String fromValue;
    private String toValue;
    private String remark;
}

