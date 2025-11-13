package org.lemon.bugtrackplatformserver.modules.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IssueAttachmentUpdateRequest {
    @NotNull
    private Long id;
    private String fileName;
    private Long fileSize;
    private String storagePath;
    private String mimeType;
}

