package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.exceptions.BadAuthException;
import com.ichtus.hotelmanagementsystem.model.dto.auth.AuthRequest;
import com.ichtus.hotelmanagementsystem.model.dto.auth.AuthResponse;
import lombok.RequiredArgsConstructor;
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
                            authRequest.getAccountName(),
                            authRequest.getAccountPassword()
                    )
            );
        }  catch (BadCredentialsException exception) {
            throw new BadAuthException();
        }

        UserDetails userDetails = accountService.loadUserByUsername(authRequest.getAccountName());
        userDetails.getAuthorities().forEach(el -> log.info(authRequest.getAccountName() + " roles= " + el.getAuthority()));

        String token = jwtTokenService.generateToken(userDetails);
        return new AuthResponse(token);
    }
}
