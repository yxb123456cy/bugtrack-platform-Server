package org.lemon.bugtrackplatformserver.modules.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectsUpdateRequest {
    @NotNull
    private Long id;
    private Long spaceId;
    private String key;
    private String name;
    private String description;
    private Long ownerId;
    private Integer status;
}

