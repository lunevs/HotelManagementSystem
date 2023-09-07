package com.ichtus.hotelmanagementsystem.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class HotelServiceTest {

    @Autowired
    HotelService hotelService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithAnonymousUser
    void notAuthorizedCall() throws Exception {
        mockMvc.perform(get("/locations"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(roles = {"ADMIN"})
    @Test
    void testWithAdminRole() throws Exception {
        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = {"USER"})
    @Test
    void testAdminZoneWithUserRole() throws Exception {
        mockMvc.perform(get("/accounts"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(roles = {"USER"})
    @Test
    void testWithUserRole() throws Exception {
        mockMvc.perform(get("/locations"))
                .andExpect(status().isOk());
    }

}
