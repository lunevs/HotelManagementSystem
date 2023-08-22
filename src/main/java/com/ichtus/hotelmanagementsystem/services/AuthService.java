package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.exceptions.AccountAlreadyExists;
import com.ichtus.hotelmanagementsystem.exceptions.BadAuthException;
import com.ichtus.hotelmanagementsystem.model.dto.account.RegistrationRequest;
import com.ichtus.hotelmanagementsystem.model.dto.account.RegistrationResponse;
import com.ichtus.hotelmanagementsystem.model.dto.auth.AuthRequest;
import com.ichtus.hotelmanagementsystem.model.dto.auth.AuthResponse;
import com.ichtus.hotelmanagementsystem.model.entities.Account;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final AccountService accountService;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse createNewToken(AuthRequest authRequest) {
        log.info("try to createNewToken: " + authRequest);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );
        }  catch (BadCredentialsException exception) {
            throw new BadAuthException();
        }

        UserDetails userDetails = accountService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenService.generateToken(userDetails);
        return new AuthResponse(token);
    }

    public RegistrationResponse createNewAccount(RegistrationRequest registrationRequest) {
        if (accountService.findByName(registrationRequest.getUsername()).isPresent()) {
            throw new AccountAlreadyExists(registrationRequest.getUsername());
        }
        Account savedAccount = accountService.createNewAccount(registrationRequest);
        return new RegistrationResponse(
                savedAccount.getId(),
                savedAccount.getAccountName(),
                savedAccount.getAccountEmail()
        );
    }
}