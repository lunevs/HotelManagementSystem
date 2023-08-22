package com.ichtus.hotelmanagementsystem.model.dto.account;


import lombok.Data;

@Data
public class RegistrationRequest {
    private String username;
    private String password;
    private String email;
}
