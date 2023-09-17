package com.ichtus.hotelmanagementsystem.units.services;

import com.ichtus.hotelmanagementsystem.model.dto.room.RequestRoomCreate;
import com.ichtus.hotelmanagementsystem.model.dto.room.ResponseRoomData;
import com.ichtus.hotelmanagementsystem.model.entities.Account;
import com.ichtus.hotelmanagementsystem.model.entities.Hotel;
import com.ichtus.hotelmanagementsystem.model.entities.Room;
import com.ichtus.hotelmanagementsystem.repository.RoomRepository;
import com.ichtus.hotelmanagementsystem.services.HotelService;
import com.ichtus.hotelmanagementsystem.services.RoomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private HotelService hotelService;

    Account account = new Account()
            .setId(0)
            .setAccountName("test account")
            .setAccountPassword("123456")
            .setAccountEmail("ya@ya.com");
    Hotel hotel = new Hotel()
            .setId(0)
            .setHotelName("test hotel")
            .setHotelDescription("sd fsdf sd fsd fsd fs dfs dfs dfjhsd fsd fs dfsd fs d")
            .setHotelCity("London")
            .setAmenities(Collections.emptyList())
            .setHotelAdmin(account);
    Room room = new Room()
            .setId(0)
            .setRoomName("test room")
            .setRoomPrice(BigDecimal.valueOf(100))
            .setHotel(hotel);
    RequestRoomCreate roomCreate = new RequestRoomCreate(
            hotel.getId(),
            room.getRoomName(),
            room.getRoomPrice(),
            room.getRoomCapacity(),
            Collections.emptyList()
    );

    @Test
    void whenFindById() {
        given(roomRepository.findById(any())).willReturn(Optional.of(room));
        assertThat(roomService.findById(1L)).isEqualTo(room);
    }

    @Test
    void whenAddRoom() {
        given(hotelService.findHotelById(1L)).willReturn(hotel);
        given(roomRepository.save(any())).willReturn(room);

        ResponseRoomData resultRoom = roomService.addRoom(roomCreate);

        assertThat(resultRoom.getRoomName()).isEqualTo(roomCreate.getRoomName());
        assertThat(resultRoom.getRoomPrice()).isEqualTo(roomCreate.getRoomPrice());
        assertThat(resultRoom.getRoomCapacity()).isEqualTo(roomCreate.getRoomMaxCapacity());
    }

    @Test
    void whenUpdateRoom() {
        given(roomRepository.findById(1L)).willReturn(Optional.of(room));
        given(roomRepository.save(any())).willReturn(room);

        ResponseRoomData resultRoom = roomService.updateRoom(1L, roomCreate);

        assertThat(resultRoom.getRoomName()).isEqualTo(roomCreate.getRoomName());
        assertThat(resultRoom.getRoomPrice()).isEqualTo(roomCreate.getRoomPrice());
        assertThat(resultRoom.getRoomCapacity()).isEqualTo(roomCreate.getRoomMaxCapacity());
    }
}
