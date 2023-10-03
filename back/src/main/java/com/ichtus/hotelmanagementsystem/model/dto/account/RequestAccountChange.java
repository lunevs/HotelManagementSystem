package com.ichtus.hotelmanagementsystem.model.dto.account;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Generated
public class RequestAccountChange {

    @NotBlank
    @Size(min = 3)
    private String accountName;

    @NotBlank
    @Size(min = 5)
    private String accountPassword;

    @NotBlank
    @Email
    private String accountEmail;
}
