package com.ichtus.hotelmanagementsystem.units.services;

import com.ichtus.hotelmanagementsystem.services.JwtTokenService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JwtTokenServiceTest {

    @Autowired
    private JwtTokenService jwtTokenService;

    private final UserDetails userDetails = new User(
            "test_username",
            "123456",
            Collections.emptyList()
    );

    @Order(0)
    @Test
    void whenGenerateToken() {
        String token = jwtTokenService.generateToken(userDetails);
        assertThat(token).isNotBlank();
    }

    @Order(1)
    @Test
    void whenValidateTokenAndGetUsername() {
        String token = jwtTokenService.generateToken(userDetails);
        String checkUsername = jwtTokenService.validateTokenAndGetUsername(token);
        assertThat(checkUsername).isEqualTo(userDetails.getUsername());
    }
}
