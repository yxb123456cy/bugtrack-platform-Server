package org.lemon.bugtrackplatformserver.modules.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SpacesUpdateRequest {
    @NotNull
    private Long id;
    private String name;
    private String key;
    private Long ownerId;
    private String description;
    private Integer visibility;
    private Integer status;
}

