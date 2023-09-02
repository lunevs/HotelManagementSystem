package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.exceptions.RoomNotFoundException;
import com.ichtus.hotelmanagementsystem.model.dto.location.GetLocationsResponseDto;
import com.ichtus.hotelmanagementsystem.model.dto.room.CreateRoomRequestDto;
import com.ichtus.hotelmanagementsystem.model.dto.room.GetRoomsResponseDto;
import com.ichtus.hotelmanagementsystem.model.entities.Location;
import com.ichtus.hotelmanagementsystem.model.entities.Room;
import com.ichtus.hotelmanagementsystem.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;
    private final LocationService locationService;

    public GetRoomsResponseDto addRoom(CreateRoomRequestDto roomRequestDto) {
        Location currentLocation = locationService.findLocationById(roomRequestDto.getLocationId());
        Room newRoom = new Room()
                .setRoomName(roomRequestDto.getRoomName())
                .setRoomPrice(roomRequestDto.getRoomPrice())
                .setRoomCapacity(roomRequestDto.getRoomMaxCapacity())
                .setLocation(currentLocation)
                .setAmenities(roomRequestDto.getAmenitiesList() == null ? Collections.emptyList() : roomRequestDto.getAmenitiesList());
        Room savedRoom = roomRepository.save(newRoom);
        log.info("addRoomToLocation1: " + savedRoom);
        return GetRoomsResponseDto.of(savedRoom);
    }

    public GetRoomsResponseDto updateRoom(Long roomId, CreateRoomRequestDto roomRequestDto) {
        Room roomToUpdate = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(roomRequestDto.getRoomName()));

        roomToUpdate
                .setRoomName(roomRequestDto.getRoomName())
                .setRoomCapacity(roomRequestDto.getRoomMaxCapacity())
                .setRoomPrice(roomRequestDto.getRoomPrice())
                .setAmenities(roomRequestDto.getAmenitiesList() == null ? Collections.emptyList() : roomRequestDto.getAmenitiesList());

        return GetRoomsResponseDto.of(roomRepository.save(roomToUpdate));
    }

}
