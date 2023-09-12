package com.ichtus.hotelmanagementsystem.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ichtus.hotelmanagementsystem.model.dto.account.RequestAccountChange;
import com.ichtus.hotelmanagementsystem.model.dto.account.ResponseAccountData;
import com.ichtus.hotelmanagementsystem.model.dto.auth.RequestAuthorization;
import com.ichtus.hotelmanagementsystem.model.dto.auth.ResponseAuthorization;
import com.ichtus.hotelmanagementsystem.model.entities.Account;
import com.ichtus.hotelmanagementsystem.services.AccountService;
import com.ichtus.hotelmanagementsystem.services.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Value("${custom.rest.basePath}")
    String basePath;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private AccountService accountService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Account account = new Account()
            .setId(1L)
            .setAccountName("test_user")
            .setAccountEmail("test@user.com")
            .setAccountPassword("123456");
    private final RequestAccountChange correctRequestAccountChange = new RequestAccountChange()
            .setAccountName(account.getAccountName())
            .setAccountEmail(account.getAccountEmail())
            .setAccountPassword(account.getAccountPassword());
    private final RequestAccountChange incorrectRequestAccountChange = new RequestAccountChange()
            .setAccountName("a")
            .setAccountEmail("email")
            .setAccountPassword("");
    private final RequestAuthorization correctRequestAuthorization = new RequestAuthorization()
            .setAccountName(account.getAccountName())
            .setAccountPassword(account.getAccountPassword());
    private final RequestAuthorization incorrectRequestAuthorization = new RequestAuthorization()
            .setAccountName("a")
            .setAccountPassword("");

    @Test
    void whenLoginUserAndReturnToken() throws Exception {
        ResponseAuthorization responseAuthorization = new ResponseAuthorization("my_token");
        given(authService.createNewToken(correctRequestAuthorization)).willReturn(responseAuthorization);

        mockMvc.perform(post(basePath + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(correctRequestAuthorization)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void whenLoginUserAndReturnToken_IncorrectInput() throws Exception {
        mockMvc.perform(post(basePath + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incorrectRequestAuthorization)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenRegisterNewUser() throws Exception {
        ResponseAccountData responseAccountData = new ResponseAccountData()
                .setId(account.getId())
                .setAccountName(account.getAccountName())
                .setAccountEmail(account.getAccountEmail());
        given(accountService.createNewAccount(any(RequestAccountChange.class)))
                .willReturn(responseAccountData);

        mockMvc.perform(post(basePath + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(correctRequestAccountChange)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(account.getId()))
                .andExpect(jsonPath("$.accountName").value(account.getAccountName()));
    }

    @Test
    void whenRegisterNewUser_IncorrectInput() throws Exception {
        mockMvc.perform(post(basePath + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incorrectRequestAccountChange)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.accountName").exists())
                .andExpect(jsonPath("$.errors.accountPassword").exists())
                .andExpect(jsonPath("$.errors.accountEmail").exists());
    }

}
