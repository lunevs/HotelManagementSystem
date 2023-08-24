package com.ichtus.hotelmanagementsystem.controllers;


import com.ichtus.hotelmanagementsystem.exceptions.AccountNotFoundException;
import com.ichtus.hotelmanagementsystem.model.anotations.IsAdministrator;
import com.ichtus.hotelmanagementsystem.model.anotations.IsUser;
import com.ichtus.hotelmanagementsystem.model.dictionaries.AccountRole;
import com.ichtus.hotelmanagementsystem.model.dictionaries.RoleUpdateActionType;
import com.ichtus.hotelmanagementsystem.model.dto.account.AccountDetailResponse;
import com.ichtus.hotelmanagementsystem.model.dto.account.RegistrationRequest;
import com.ichtus.hotelmanagementsystem.model.dto.account.AccountUpdateRequest;
import com.ichtus.hotelmanagementsystem.services.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/userinfo")
    @IsAdministrator
    public ResponseEntity<?> userInfo(Principal principal) {
        return ResponseEntity.ok(accountService.loadUserByUsername(principal.getName()));
    }


    @PostMapping
    @IsAdministrator
    public ResponseEntity<?> createNewUserAccount(@RequestBody RegistrationRequest registrationRequest) {
        return ResponseEntity.ok(accountService.createNewAccount(registrationRequest));
    }

    @GetMapping
    @IsAdministrator
    public ResponseEntity<?> getAllAccounts() {
        return ResponseEntity.ok(accountService.findAll());
    }

    @GetMapping("/{id}")
    @IsAdministrator
    public ResponseEntity<?> getAccountDetails(@PathVariable Long id) {
        return ResponseEntity.ok(
                accountService
                        .findById(id)
                        .map(AccountDetailResponse::of)
                        .orElseThrow(() -> new AccountNotFoundException(id))
        );
    }

    @PutMapping("/{id}/info")
    @IsAdministrator
    public ResponseEntity<?> updateAccountInfo(@PathVariable Long id, @Valid @RequestBody AccountUpdateRequest accountUpdateRequest) {
        return ResponseEntity.ok(accountService.accountUpdateInfo(id, accountUpdateRequest));
    }

    @PutMapping("/{id}/role?{action}={value}")
    @IsAdministrator
    public ResponseEntity<?> updateAccountRole(
            @PathVariable Long id,
            @PathVariable RoleUpdateActionType action,
            @PathVariable AccountRole value) {

        return ResponseEntity.ok(accountService.accountUpdateRoles(id, action, value));
    }

    @DeleteMapping("/{id}")
    @IsAdministrator
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
