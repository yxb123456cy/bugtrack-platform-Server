package org.lemon.bugtrackplatformserver.modules.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TagsCreateRequest {
    @NotNull
    private Long projectId;
    @NotBlank
    private String name;
    private String color;
}

