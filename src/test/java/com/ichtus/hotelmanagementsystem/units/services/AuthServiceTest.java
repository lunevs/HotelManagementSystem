package com.ichtus.hotelmanagementsystem.units.services;

import com.ichtus.hotelmanagementsystem.model.dto.auth.RequestAuthorization;
import com.ichtus.hotelmanagementsystem.model.dto.auth.ResponseAuthorization;
import com.ichtus.hotelmanagementsystem.services.AccountService;
import com.ichtus.hotelmanagementsystem.services.AuthService;
import com.ichtus.hotelmanagementsystem.services.JwtTokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @MockBean
    private AccountService accountService;

    @MockBean
    private JwtTokenService jwtTokenService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    void whenCreateNewToken () {

        UserDetails userDetails = new User("test_username", "123456", Collections.emptyList());
        String token = "MY_NEW_TOKEN";

        given(accountService.loadUserByUsername(any())).willReturn(userDetails);
        given(jwtTokenService.generateToken(any())).willReturn(token);

        ResponseAuthorization authorization = authService.createNewToken(
                new RequestAuthorization()
                        .setAccountName("test_username")
                        .setAccountPassword("123456")
        );

        assertThat(authorization.getToken()).isEqualTo(token);
    }
}
