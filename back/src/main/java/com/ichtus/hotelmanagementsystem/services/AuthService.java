package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.model.dto.auth.RequestAuthorization;
import com.ichtus.hotelmanagementsystem.model.dto.auth.ResponseAuthorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Defines authentication services
 * @author smlunev
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountService accountService;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;

    /**
     * Create authentication user token
     * @param requestAuthorization dto with user login and password
     * @return dto ResponseAuthorization with user token
     */
    public ResponseAuthorization createNewToken(RequestAuthorization requestAuthorization) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestAuthorization.getAccountName(),
                            requestAuthorization.getAccountPassword()
                    )
            );
        }  catch (BadCredentialsException exception) {
            throw new BadCredentialsException("Login/password incorrect");
        }

        UserDetails userDetails = accountService.loadUserByUsername(requestAuthorization.getAccountName());
        String token = jwtTokenService.generateToken(userDetails);

        return new ResponseAuthorization(token);
    }
}
