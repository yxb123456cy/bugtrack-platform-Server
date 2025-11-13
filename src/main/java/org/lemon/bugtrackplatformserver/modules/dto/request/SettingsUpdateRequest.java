package org.lemon.bugtrackplatformserver.modules.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SettingsUpdateRequest {
    @NotNull
    private Long id;
    private Long projectId;
    private String key;
    private String value;
}

