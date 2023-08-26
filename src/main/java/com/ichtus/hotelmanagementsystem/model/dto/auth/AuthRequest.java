package com.ichtus.hotelmanagementsystem.model.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequest {

    @NotBlank
    private String accountName;

    @NotBlank
    private String accountPassword;
}
