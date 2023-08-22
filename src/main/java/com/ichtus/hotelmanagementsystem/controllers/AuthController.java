package com.ichtus.hotelmanagementsystem.controllers;

import com.ichtus.hotelmanagementsystem.model.dto.account.RegistrationRequest;
import com.ichtus.hotelmanagementsystem.model.dto.auth.AuthRequest;
import com.ichtus.hotelmanagementsystem.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUserAndReturnToken(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.createNewToken(authRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@RequestBody RegistrationRequest registrationRequest) {
        return ResponseEntity.ok(authService.createNewAccount(registrationRequest));
    }
}
