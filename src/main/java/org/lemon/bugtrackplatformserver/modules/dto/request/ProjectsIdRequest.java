package org.lemon.bugtrackplatformserver.modules.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectsIdRequest {
    @NotNull
    private Long id;
}

