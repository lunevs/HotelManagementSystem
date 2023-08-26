package com.ichtus.hotelmanagementsystem.model.dto.account;

import com.ichtus.hotelmanagementsystem.model.entities.Role;
import lombok.Value;

import java.util.Collection;

@Value
public class RegistrationResponse {

    Long id;
    String accountName;
    String accountEmail;
    Role role;
}
