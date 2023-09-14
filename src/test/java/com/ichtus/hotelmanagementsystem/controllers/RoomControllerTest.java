package com.ichtus.hotelmanagementsystem.controllers;

import com.ichtus.hotelmanagementsystem.exceptions.HotelNotFoundException;
import com.ichtus.hotelmanagementsystem.model.anotations.IsModerator;
import com.ichtus.hotelmanagementsystem.model.anotations.WithMockAdmin;
import com.ichtus.hotelmanagementsystem.model.dto.amenity.RequestAmenityChange;
import com.ichtus.hotelmanagementsystem.model.dto.room.RequestRoomCreate;
import com.ichtus.hotelmanagementsystem.model.dto.room.ResponseRoomData;
import com.ichtus.hotelmanagementsystem.services.RoomService;
import jakarta.validation.Valid;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RoomControllerTest {

    @Value("${custom.rest.basePath}/rooms")
    String basePath;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomService roomService;

    String correctInput = "{\"hotelId\":\"1\"," +
            "\"roomName\":\"test room\"," +
            "\"roomPrice\":\"100\"," +
            "\"roomMaxCapacity\":\"5\"," +
            "\"amenitiesList\":[]}";
    String incorrectInput = "{\"hotelId\":\"1\"," +
            "\"roomName\":\"t\"," +
            "\"roomPrice\":\"-5\"," +
            "\"roomMaxCapacity\":\"500\"," +
            "\"amenitiesList\":[]}";
    ResponseRoomData roomData = new ResponseRoomData()
            .setId(1L)
            .setRoomName("test room")
            .setRoomPrice(BigDecimal.valueOf(100))
            .setRoomCapacity(5);

    @Test
    @WithAnonymousUser
    void whenAddRoom_WithoutAuth() throws Exception {
        mockMvc.perform(post(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(correctInput))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockAdmin
    void whenAddRoom_asAdmin() throws Exception {

        given(roomService.addRoom(any(RequestRoomCreate.class))).willReturn(roomData);
        mockMvc.perform(post(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(correctInput))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomName").exists())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @WithMockAdmin
    void whenAddRoom_asAdmin_IncorrectInput() throws Exception {

        mockMvc.perform(post(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(incorrectInput))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.roomName").exists())
                .andExpect(jsonPath("$.errors.roomMaxCapacity").exists())
                .andExpect(jsonPath("$.errors.roomPrice").exists());
    }

    @Test
    @WithAnonymousUser
    void whenUpdateRoom_WithoutAuth() throws Exception {
        mockMvc.perform(put(basePath + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(correctInput))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockAdmin
    void whenUpdateRoom_asAdmin() throws Exception {

        given(roomService.updateRoom(any(Long.class), any(RequestRoomCreate.class))).willReturn(roomData);
        mockMvc.perform(put(basePath + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(correctInput))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomName").exists())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @WithMockAdmin
    void whenUpdateRoom_asAdmin_IncorrectId() throws Exception {
        willThrow(new HotelNotFoundException(155L)).given(roomService).updateRoom(any(), any());
        mockMvc.perform(put(basePath + "/155")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(correctInput))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockAdmin
    void whenUpdateRoom_asAdmin_IncorrectInput() throws Exception {

        mockMvc.perform(put(basePath + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(incorrectInput))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.roomName").exists())
                .andExpect(jsonPath("$.errors.roomMaxCapacity").exists())
                .andExpect(jsonPath("$.errors.roomPrice").exists());
    }
}
