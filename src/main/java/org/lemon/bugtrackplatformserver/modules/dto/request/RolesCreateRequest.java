package org.lemon.bugtrackplatformserver.modules.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RolesCreateRequest {
    @NotBlank
    private String name;
    private String description;
}

