package com.ichtus.hotelmanagementsystem.model.dto.account;


import lombok.Data;

@Data
public class AccountUpdateRequest {

    private String accountName;
    private String accountPassword;
    private String accountEmail;
}
