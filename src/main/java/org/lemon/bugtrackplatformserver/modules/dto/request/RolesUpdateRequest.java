package org.lemon.bugtrackplatformserver.modules.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RolesUpdateRequest {
    @NotNull
    private Integer id;
    private String name;
    private String description;
}

