package com.ichtus.hotelmanagementsystem.units.services;

import com.ichtus.hotelmanagementsystem.services.HotelService;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${custom.rest.basePath}")
    String basePath;

    @Test
    @WithAnonymousUser
    void notAuthorizedCall() throws Exception {
        mockMvc.perform(get(basePath + "/hotels"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser
    @Test
    void testWithUserRole() throws Exception {
        mockMvc.perform(get(basePath + "/hotels"))
                .andExpect(status().isOk());
    }

}
