package org.lemon.bugtrackplatformserver.modules.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsersUpdateRequest {
    @NotNull
    private Long id;
    @Email
    private String email;
    private String displayName;
    private String passwordHash;
    private Integer status;
}
