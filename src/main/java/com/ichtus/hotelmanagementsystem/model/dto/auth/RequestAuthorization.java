package com.ichtus.hotelmanagementsystem.model.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestAuthorization {

    @NotBlank
    private String accountName;

    @NotBlank
    private String accountPassword;
}
