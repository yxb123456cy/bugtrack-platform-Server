package org.lemon.bugtrackplatformserver.modules.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IssueAttachmentResponse {
    private Long id;
    private Long issueId;
    private Long uploaderId;
    private String fileName;
    private Long fileSize;
    private String storagePath;
    private String mimeType;
    private LocalDateTime createdAt;
}

