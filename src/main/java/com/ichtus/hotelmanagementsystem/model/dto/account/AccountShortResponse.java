package com.ichtus.hotelmanagementsystem.model.dto.account;

import com.ichtus.hotelmanagementsystem.model.entities.Account;
import com.ichtus.hotelmanagementsystem.model.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Collection;

@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountShortResponse {

    Long id;
    String accountName;
    String accountEmail;
    Role role;


    public static AccountShortResponse of(Account account) {
        return new AccountShortResponse()
                .setId(account.getId())
                .setAccountName(account.getAccountName())
                .setAccountEmail(account.getAccountEmail())
                .setRole(account.getRole());
    }
}
