package com.ichtus.hotelmanagementsystem.model.dto.account;

import lombok.Value;

@Value
public class CreateAccountRequestDto {

    String name;
    String password;
    String email;
    String description;
}
