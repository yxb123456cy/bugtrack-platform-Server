package org.lemon.bugtrackplatformserver.modules.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsersCreateRequest {
    @NotBlank
    private String username;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String displayName;
    @NotBlank
    private String passwordHash;
    private Integer status;
}

