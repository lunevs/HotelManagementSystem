package com.ichtus.hotelmanagementsystem.controllers;


import com.ichtus.hotelmanagementsystem.model.anotations.IsAdministrator;
import com.ichtus.hotelmanagementsystem.model.dto.account.RequestAccountChange;
import com.ichtus.hotelmanagementsystem.model.dto.account.ResponseAccountData;
import com.ichtus.hotelmanagementsystem.model.dto.account.RequestAccountRoleChange;
import com.ichtus.hotelmanagementsystem.services.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> createNewUserAccount(@RequestBody RequestAccountChange requestAccountChange) {
        return ResponseEntity.ok(accountService.createNewAccount(requestAccountChange));
    }

    @GetMapping
    @IsAdministrator
    public ResponseEntity<?> getAllAccounts() {
        return ResponseEntity.ok(accountService.findAll());
    }

    @GetMapping("/{id}")
    @IsAdministrator
    public ResponseEntity<?> getAccountDetails(@PathVariable Long id) {
        return ResponseEntity.ok(ResponseAccountData.of(accountService.findAccountById(id)));
    }

    @PutMapping("/{id}/info")
    @IsAdministrator
    public ResponseEntity<?> updateAccountInfo(@PathVariable Long id, @Valid @RequestBody RequestAccountChange accountUpdateRequest) {
        return ResponseEntity.ok(accountService.accountUpdateInfo(id, accountUpdateRequest));
    }

    @PutMapping("/{id}/role")
    @IsAdministrator
    public ResponseEntity<?> updateAccountRole(@PathVariable Long id, @RequestBody RequestAccountRoleChange roleChange) {
        return ResponseEntity.ok(accountService.accountUpdateRole(id, roleChange.getRole().name()));
    }

    @DeleteMapping("/{id}")
    @IsAdministrator
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
