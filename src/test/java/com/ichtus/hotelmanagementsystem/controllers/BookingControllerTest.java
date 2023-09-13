package com.ichtus.hotelmanagementsystem.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ichtus.hotelmanagementsystem.exceptions.FreeDatesForRoomNotFountException;
import com.ichtus.hotelmanagementsystem.model.anotations.WithMockAdmin;
import com.ichtus.hotelmanagementsystem.model.dto.booking.RequestNewBooking;
import com.ichtus.hotelmanagementsystem.model.dto.booking.ResponseBooking;
import com.ichtus.hotelmanagementsystem.services.BookingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.*;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingControllerTest {

    @Value("${custom.rest.basePath}/bookings")
    String basePath;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RequestNewBooking requestNewBooking = new RequestNewBooking()
            .setRoomId(1L)
            .setHotelId(1L)
            .setStartDate(Date.from(Instant.now().plusMillis(24*60*60*10)))
            .setEndDate(Date.from(Instant.now().plusMillis(24*60*60*20)))
            .setNumberOfGuests(2);

    @Test
    @WithMockUser
    void whenGetAll() throws Exception {
        ResponseBooking responseBooking = new ResponseBooking()
                .setId(1L)
                .setAccountId(1L)
                .setAccountName("test_user");
        given(bookingService.getMyBookings()).willReturn(Collections.singletonList(responseBooking));

        mockMvc.perform(get(basePath))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].accountName").value(responseBooking.getAccountName()));
    }

    @Test
    @WithMockUser
    void whenDoBooking() throws Exception {
        given(bookingService.doBooking(requestNewBooking, "admin"))
                .willReturn(new ResponseBooking());

        mockMvc.perform(post(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestNewBooking)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockAdmin
    void whenDoBooking_IncorrectInput() throws Exception {
        String incorrectData = "{" +
                "\"roomId\":\"-4\"," +
                "\"hotelId\":\"\"," +
                "\"numberOfGuests\":\"15\"," +
                "\"startDate\":\"2021-01-01\"," +
                "\"endDate\":\"2022-01-01\"}";
        mockMvc.perform(post(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(incorrectData))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.roomId").exists())
                .andExpect(jsonPath("$.errors.hotelId").exists())
                .andExpect(jsonPath("$.errors.numberOfGuests").exists())
                .andExpect(jsonPath("$.errors.startDate").exists())
                .andExpect(jsonPath("$.errors.endDate").exists())
                .andDo(print());
    }

}
