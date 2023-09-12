package com.ichtus.hotelmanagementsystem.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ichtus.hotelmanagementsystem.model.anotations.WithMockAdmin;
import com.ichtus.hotelmanagementsystem.model.dictionaries.AmenityType;
import com.ichtus.hotelmanagementsystem.model.dto.amenity.RequestAmenityChange;
import com.ichtus.hotelmanagementsystem.model.entities.Amenity;
import com.ichtus.hotelmanagementsystem.repository.AmenityRepository;
import com.ichtus.hotelmanagementsystem.services.AmenityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AmenityControllerTest {

    @Value("${custom.rest.basePath}/amenities")
    String basePath;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AmenityService amenityService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RequestAmenityChange amenityChange = new RequestAmenityChange(
            "test_amenity",
            "some amenity description",
            10.5F,
            AmenityType.HOTEL
    );
    private final Amenity amenity = new Amenity()
            .setId(1L)
            .setAmenityName(amenityChange.getAmenityName())
            .setAmenityType(amenityChange.getAmenityType())
            .setAmenityPrice(amenityChange.getAmenityPrice())
            .setAmenityDescription(amenityChange.getAmenityDescription());


    @Test
    @WithAnonymousUser
    void whenCreateAmenity_WithoutAuth_thenReturnsStatus400() throws Exception {
        mockMvc.perform(post(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(amenityChange)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockAdmin
    void whenCreateAmenity_AsAdmin_thenReturnsOk() throws Exception {

        given(amenityService.createAmenity(any(RequestAmenityChange.class))).willReturn(amenity);

        mockMvc.perform(post(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(amenityChange)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amenityName").value(amenityChange.getAmenityName()));
    }

    @Test
    @WithAnonymousUser
    void whenUpdateAmenity_WithoutAuth_thenForbidden() throws Exception {
        mockMvc.perform(put(basePath + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(amenityChange)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockAdmin
    void whenUpdateAmenity_asAdmin_thenOk() throws Exception {

        given(amenityService.updateAmenity(any(Long.class), any(RequestAmenityChange.class))).willReturn(amenity);

        mockMvc.perform(put(basePath + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(amenityChange)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amenityName").value(amenityChange.getAmenityName()));
    }
}
