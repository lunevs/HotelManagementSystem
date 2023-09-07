package com.ichtus.hotelmanagementsystem.controllers;

import com.ichtus.hotelmanagementsystem.model.dto.account.RequestAccountChange;
import com.ichtus.hotelmanagementsystem.model.dto.auth.RequestAuthorization;
import com.ichtus.hotelmanagementsystem.services.AccountService;
import com.ichtus.hotelmanagementsystem.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUserAndReturnToken(@RequestBody RequestAuthorization requestAuthorization) {
        return ResponseEntity.ok(authService.createNewToken(requestAuthorization));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@RequestBody RequestAccountChange requestAccountChange) {
        return ResponseEntity.ok(accountService.createNewAccount(requestAccountChange));
    }
}
