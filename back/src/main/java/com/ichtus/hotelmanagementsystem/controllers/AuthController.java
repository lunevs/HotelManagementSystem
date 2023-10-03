package com.ichtus.hotelmanagementsystem.controllers;

import com.ichtus.hotelmanagementsystem.model.dto.account.RequestAccountChange;
import com.ichtus.hotelmanagementsystem.model.dto.account.ResponseAccountData;
import com.ichtus.hotelmanagementsystem.model.dto.auth.RequestAuthorization;
import com.ichtus.hotelmanagementsystem.model.dto.auth.ResponseAuthorization;
import com.ichtus.hotelmanagementsystem.services.AccountService;
import com.ichtus.hotelmanagementsystem.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for authentication and registration new users
 *
 * @author smlunev
 */
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AccountService accountService;

    /**
     * Endpoint to authentication users
     * @param requestAuthorization dto with user login and password
     * @return token if success authentication
     */
    @PostMapping("/login")
    public ResponseEntity<ResponseAuthorization> loginUserAndReturnToken(@Valid @RequestBody RequestAuthorization requestAuthorization) {
        return ResponseEntity.ok(authService.createNewToken(requestAuthorization));
    }

    /**
     * Endpoint to register new user account
     * @param requestAccountChange dto with new user data
     * @return ResponseAccountData with new account information
     */
    @PostMapping("/register")
    public ResponseEntity<ResponseAccountData> registerNewUser(@Valid @RequestBody RequestAccountChange requestAccountChange) {
        return ResponseEntity.ok(accountService.createNewAccount(requestAccountChange));
    }

    /**
     * Endpoint to handle requests with incorrect url
     * @return empty response with status 400
     */
    @PostMapping("/error")
    public ResponseEntity<?> failedRequest() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
