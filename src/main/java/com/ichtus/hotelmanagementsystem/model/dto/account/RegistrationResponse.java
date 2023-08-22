package com.ichtus.hotelmanagementsystem.model.dto.account;

import lombok.Value;

@Value
public class RegistrationResponse {

    Long id;
    String username;
    String email;
}
