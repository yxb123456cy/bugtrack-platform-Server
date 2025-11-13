package org.lemon.bugtrackplatformserver.modules.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SettingsCreateRequest {
    @NotNull
    private Long projectId;
    @NotBlank
    private String key;
    @NotBlank
    private String value;
}

