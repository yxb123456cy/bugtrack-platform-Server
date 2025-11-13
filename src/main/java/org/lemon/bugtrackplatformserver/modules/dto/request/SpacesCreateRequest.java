package org.lemon.bugtrackplatformserver.modules.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SpacesCreateRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String key;
    @NotNull
    private Long ownerId;
    private String description;
    private Integer visibility;
    private Integer status;
}

