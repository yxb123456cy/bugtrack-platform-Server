package org.lemon.bugtrackplatformserver.modules.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TagsUpdateRequest {
    @NotNull
    private Long id;
    private String name;
    private String color;
}

