package com.ichtus.hotelmanagementsystem.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ichtus.hotelmanagementsystem.model.anotations.WithMockAdmin;
import com.ichtus.hotelmanagementsystem.model.dto.account.RequestAccountChange;
import com.ichtus.hotelmanagementsystem.model.dto.account.ResponseAccountData;
import com.ichtus.hotelmanagementsystem.model.entities.Role;
import com.ichtus.hotelmanagementsystem.services.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;


@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Value("${custom.rest.basePath}/accounts")
    String basePath;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    RequestAccountChange correctRequestAccountChange = new RequestAccountChange()
            .setAccountName("test_admin")
            .setAccountPassword("123456")
            .setAccountEmail("admin@admin.com");
    RequestAccountChange incorrectRequestAccountChange = new RequestAccountChange()
            .setAccountName("a")
            .setAccountPassword("")
            .setAccountEmail("admin.com");
    ResponseAccountData responseAccountData = new ResponseAccountData()
            .setId(1L)
            .setAccountName(correctRequestAccountChange.getAccountName())
            .setAccountEmail(correctRequestAccountChange.getAccountEmail())
            .setRole(new Role().setName("ROLE_USER"));

    @Test
    @WithAnonymousUser
    void whenCreateNewAccount_WithoutAuth_thenReturnsStatus400() throws Exception {
        mockMvc.perform(post(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(correctRequestAccountChange)))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockAdmin
    void whenCreateNewAccount_UsernameIsInvalid_thenReturnsStatus400() throws Exception {
        mockMvc.perform(post(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incorrectRequestAccountChange)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.accountName").exists())
                .andExpect(jsonPath("$.errors.accountPassword").exists())
                .andExpect(jsonPath("$.errors.accountEmail").exists());
    }

    @Test
    @WithMockUser
    void whenGetAllAccounts_WithUserRole_thenForbidden() throws Exception {
        mockMvc.perform(get(basePath))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockAdmin
    void whenGetAllAccounts_WithUserAdmin_thenOk() throws Exception {
        given(accountService.findAll()).willReturn(List.of(responseAccountData));

        mockMvc.perform(get(basePath))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(responseAccountData.getId()));
    }

    @Test
    @WithMockAdmin
    void whenUpdateAccountInfo_WithUserAdmin_thenOk() throws Exception {
        given(accountService.accountUpdateInfo(any(Long.class), any(RequestAccountChange.class)))
                .willReturn(responseAccountData);

        mockMvc.perform(put(basePath + "/" + responseAccountData.getId() + "/info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(correctRequestAccountChange)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountName").value(responseAccountData.getAccountName()));
    }

    @Test
    @WithMockAdmin
    void whenUpdateAccountInfo_WithUserAdmin_IncorrectInput_thenBadRequest() throws Exception {
        mockMvc.perform(put(basePath + "/" + responseAccountData.getId() + "/info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(incorrectRequestAccountChange)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.accountEmail").exists())
                .andExpect(jsonPath("$.errors.accountName").exists())
                .andExpect(jsonPath("$.errors.accountPassword").exists());
    }

    @Test
    @WithMockUser
    void whenUpdateAccountInfo_WithUser_thenForbidden() throws Exception {
        mockMvc.perform(put(basePath + "/" + responseAccountData.getId() + "/info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(correctRequestAccountChange)))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockAdmin
    void whenUpdateAccountRole_WithUserAdmin_thenOk() throws Exception {

        given(accountService.accountUpdateRole(responseAccountData.getId(), "ADMIN"))
                .willReturn(responseAccountData.setRole(new Role().setName("ROLE_ADMIN")));

        String inputData = "{\"action\":\"ADD\",\"role\":\"ADMIN\"}";
        MvcResult mvcResult = mockMvc.perform(put(basePath + "/1/role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputData))
                .andExpect(status().isOk())
                .andReturn();

        ResponseAccountData accountData = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ResponseAccountData.class);
        assertThat(accountData.getRole().getName().contains("USER")).isFalse();
        assertThat(accountData.getRole().getName().contains("ADMIN")).isTrue();

    }

    @Test
    @WithMockUser
    void whenUpdateAccountRole_WithUser_thenForbidden() throws Exception {
        String inputData = "{\"action\":\"ADD\",\"role\":\"ADMIN\"}";
        mockMvc.perform(put(basePath + "/1/role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputData))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockAdmin
    void whenDeleteAccount_WithUserAdmin_thenOk() throws Exception {
        mockMvc.perform(delete(basePath + "/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void whenDeleteAccount_WithUser_thenForbidden() throws Exception {
        mockMvc.perform(delete(basePath + "/1"))
                .andExpect(status().isForbidden());
    }

}
