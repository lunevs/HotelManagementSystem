package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.exceptions.RoomNotFoundException;
import com.ichtus.hotelmanagementsystem.model.dto.room.RequestRoomCreate;
import com.ichtus.hotelmanagementsystem.model.dto.room.ResponseRoomData;
import com.ichtus.hotelmanagementsystem.model.entities.Hotel;
import com.ichtus.hotelmanagementsystem.model.entities.Room;
import com.ichtus.hotelmanagementsystem.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;
    private final HotelService hotelService;

    public Room findById(Long id) {
        return roomRepository.findById(id).orElseThrow(() -> new RoomNotFoundException(id.toString()));
    }

    public ResponseRoomData addRoom(RequestRoomCreate roomRequestDto) {
        Hotel currentHotel = hotelService.findHotelById(roomRequestDto.getLocationId());
        Room newRoom = new Room()
                .setRoomName(roomRequestDto.getRoomName())
                .setRoomPrice(roomRequestDto.getRoomPrice())
                .setRoomCapacity(roomRequestDto.getRoomMaxCapacity())
                .setHotel(currentHotel)
                .setAmenities(roomRequestDto.getAmenitiesList() == null ? Collections.emptyList() : roomRequestDto.getAmenitiesList());
        Room savedRoom = roomRepository.save(newRoom);
        log.info("addRoomToLocation1: " + savedRoom);
        return ResponseRoomData.of(savedRoom);
    }

    public ResponseRoomData updateRoom(Long roomId, RequestRoomCreate roomRequestDto) {
        Room roomToUpdate = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(roomRequestDto.getRoomName()));
        roomToUpdate
                .setRoomName(roomRequestDto.getRoomName())
                .setRoomCapacity(roomRequestDto.getRoomMaxCapacity())
                .setRoomPrice(roomRequestDto.getRoomPrice())
                .setAmenities(roomRequestDto.getAmenitiesList() == null ? Collections.emptyList() : roomRequestDto.getAmenitiesList());

        return ResponseRoomData.of(roomRepository.save(roomToUpdate));
    }

}
