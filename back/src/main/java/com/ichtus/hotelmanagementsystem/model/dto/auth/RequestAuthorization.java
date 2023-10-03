package com.ichtus.hotelmanagementsystem.model.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RequestAuthorization {

    @NotBlank
    @Size(min = 3)
    private String accountName;

    @NotBlank
    @Size(min = 3)
    private String accountPassword;
}
