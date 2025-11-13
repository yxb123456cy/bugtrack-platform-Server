package org.lemon.bugtrackplatformserver.modules.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IssueAttachmentCreateRequest {
    @NotNull
    private Long issueId;
    @NotNull
    private Long uploaderId;
    @NotBlank
    private String fileName;
    @NotNull
    private Long fileSize;
    @NotBlank
    private String storagePath;
    private String mimeType;
}

