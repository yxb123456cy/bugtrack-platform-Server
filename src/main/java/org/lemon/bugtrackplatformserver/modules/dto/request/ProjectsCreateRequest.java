package org.lemon.bugtrackplatformserver.modules.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectsCreateRequest {
    @NotNull
    private Long spaceId;
    @NotBlank
    private String key;
    @NotBlank
    private String name;
    private String description;
    @NotNull
    private Long ownerId;
    private Integer status;
}

