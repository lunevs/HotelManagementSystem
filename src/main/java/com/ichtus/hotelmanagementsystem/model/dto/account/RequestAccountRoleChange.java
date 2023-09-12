package com.ichtus.hotelmanagementsystem.model.dto.account;

import com.ichtus.hotelmanagementsystem.model.dictionaries.AccountRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestAccountRoleChange {

    @NotBlank
    private AccountRole role;

    private String description;
}
