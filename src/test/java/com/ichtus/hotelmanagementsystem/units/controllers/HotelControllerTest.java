package com.ichtus.hotelmanagementsystem.units.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ichtus.hotelmanagementsystem.exceptions.HotelNotFoundException;
import com.ichtus.hotelmanagementsystem.utils.anotations.WithMockAdmin;
import com.ichtus.hotelmanagementsystem.model.dto.hotels.RequestHotelChange;
import com.ichtus.hotelmanagementsystem.model.dto.hotels.ResponseHotelData;
import com.ichtus.hotelmanagementsystem.services.HotelService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class HotelControllerTest {

    @Value("${custom.rest.basePath}/hotels")
    String basePath;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HotelService hotelService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    ResponseHotelData hotelData = new ResponseHotelData()
            .setId(1L)
            .setHotelName("test hotel")
            .setHotelDescription("some hotel description some hotel description")
            .setHotelCity("London")
            .setAdminName("admin");

    @Test
    @WithMockUser
    void whenGetHotelsCities() throws Exception {
        Set<String> cities = Set.of("city1", "city2");
        given(hotelService.getHotelsCities()).willReturn(cities);
        mockMvc.perform(get(basePath + "/cities"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @WithMockAdmin
    void whenCreateHotel() throws Exception {
        given(hotelService.addHotel(ArgumentMatchers.any(RequestHotelChange.class), ArgumentMatchers.any(String.class)))
                .willReturn(hotelData);
        mockMvc.perform(post(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"hotelName\":\"test hotel\",\"hotelDescription\":\"some hotel description some hotel description\",\"hotelCity\":\"London\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.hotelName").value(hotelData.getHotelName()));
    }

    @Test
    @WithMockAdmin
    void whenCreateHotel_IncorrectInput() throws Exception {
        mockMvc.perform(post(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"hotelName\":\"t\",\"hotelDescription\":\"some...\",\"hotelCity\":\"p\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void whenGetHotelInfo() throws Exception {
        given(hotelService.getHotelInfo(ArgumentMatchers.any(Long.class))).willReturn(hotelData);
        mockMvc.perform(get(basePath + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hotelName").value(hotelData.getHotelName()));
    }

    @Test
    @WithMockUser
    void whenGetHotelInfo_IncorrectInput() throws Exception {
        given(hotelService.getHotelInfo(ArgumentMatchers.any(Long.class)))
                .willThrow(new HotelNotFoundException(1L));
        mockMvc.perform(get(basePath + "/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Hotel not found"));
    }

    @Test
    @WithMockAdmin
    void whenUpdateHotelInfo_IncorrectInput() throws Exception {
        mockMvc.perform(put(basePath + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Message Not Readable"));
    }

    @Test
    @WithMockAdmin
    void whenUpdateHotelInfo_IncorrectId() throws Exception {
        given(hotelService.updateHotelInfo(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .willThrow(new HotelNotFoundException(1L));
        mockMvc.perform(put(basePath + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"hotelName\":\"Отель в горах\",\"hotelDescription\":\"описание нашего классного отеля в горах\",\"hotelCity\":\"Milan\"}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Hotel not found"));
    }

    @Test
    @WithMockAdmin
    void whenUpdateHotelInfo() throws Exception {
        given(hotelService.updateHotelInfo(ArgumentMatchers.any(Long.class), ArgumentMatchers.any())).willReturn(hotelData);
        mockMvc.perform(put(basePath + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"hotelName\":\"test hotel\",\"hotelDescription\":\"some hotel description some hotel description\",\"hotelCity\":\"London\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hotelName").value(hotelData.getHotelName()));
    }

    @Test
    @WithMockAdmin
    void whenDeleteHotel() throws Exception {
        willDoNothing().given(hotelService).deleteHotel(ArgumentMatchers.any());
        mockMvc.perform(delete(basePath + "/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockAdmin
    void whenDeleteHotel_IncorrectId() throws Exception {
        willThrow(new HotelNotFoundException(155L)).given(hotelService).deleteHotel(ArgumentMatchers.any());
        mockMvc.perform(delete(basePath + "/155"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void whenGetHotelRoomsList() throws Exception {
        willReturn(Collections.singletonList(hotelData))
                .given(hotelService).getRoomsList(ArgumentMatchers.any());
        mockMvc.perform(get(basePath + "/1/rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @WithMockUser
    void whenGetHotelRoomsList_IncorrectId() throws Exception {
        willThrow(new HotelNotFoundException(155L))
                .given(hotelService).getRoomsList(ArgumentMatchers.any());
        mockMvc.perform(get(basePath + "/155/rooms"))
                .andExpect(status().isNotFound());
    }

}
