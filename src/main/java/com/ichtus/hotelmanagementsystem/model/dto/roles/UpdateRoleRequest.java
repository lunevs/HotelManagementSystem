package com.ichtus.hotelmanagementsystem.model.dto.roles;

import com.ichtus.hotelmanagementsystem.model.dictionaries.AccountRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateRoleRequest {

    @NotBlank
    private AccountRole role;
    private String description;
}
