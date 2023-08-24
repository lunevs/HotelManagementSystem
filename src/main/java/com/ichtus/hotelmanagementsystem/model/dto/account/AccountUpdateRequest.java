package com.ichtus.hotelmanagementsystem.model.dto.account;


import lombok.Data;

@Data
public class AccountUpdateRequest {

    private String username;
    private String password;
    private String email;
}
