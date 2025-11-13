package org.lemon.bugtrackplatformserver.modules.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class NotificationPageQueryRequest {
    @Min(1)
    private int pageNum = 1;
    @Min(1)
    private int pageSize = 10;
    private Long userId;
    private Integer isRead;
    private String type;
}

