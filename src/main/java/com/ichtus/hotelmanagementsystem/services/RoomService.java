package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.model.dao.Location;
import com.ichtus.hotelmanagementsystem.model.dao.Room;
import com.ichtus.hotelmanagementsystem.model.dto.CreateRoomRequestDto;
import com.ichtus.hotelmanagementsystem.model.dto.GetRoomsResponseDto;
import com.ichtus.hotelmanagementsystem.repository.LocationRepository;
import com.ichtus.hotelmanagementsystem.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final LocationRepository locationRepository;

//    public List<GetRoomsResponseDto> getRoomsList(Location location) {
//        return StreamSupport
//                .stream(roomRepository.searchRoomsBy(location).spliterator(), false)
//                .map(room -> new GetRoomsResponseDto(
//                        room.getId(),
//                        room.getRoomName(),
//                        room.getRoomPrice(),
//                        room.getRoomCapacity(),
//                        location.getId()
//                ))
//                .toList();
//    }

//    public Room addRoomToLocation(Long id, CreateRoomRequestDto roomRequestDto) {
//        Location currentLocation = locationRepository.findById(id).orElseThrow();
//        Room newRoom = new Room()
//                .setRoomName(roomRequestDto.getRoomName())
//                .setRoomPrice(roomRequestDto.getRoomPrice())
//                .setRoomCapacity(roomRequestDto.getRoomMaxCapacity())
//                .setLocation(currentLocation);
//        return roomRepository.save(newRoom);
//    }
//
//    public void deleteRoomsByLocation(Long id) {
//        Location currentLocation = locationRepository.findById(id).orElseThrow();
//        roomRepository.deleteAllBy(currentLocation);
//    }

}
