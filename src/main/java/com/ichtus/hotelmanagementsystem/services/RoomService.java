package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.model.dto.room.RequestRoomCreate;
import com.ichtus.hotelmanagementsystem.model.dto.room.ResponseRoomData;
import com.ichtus.hotelmanagementsystem.model.entities.Hotel;
import com.ichtus.hotelmanagementsystem.model.entities.Room;
import com.ichtus.hotelmanagementsystem.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectDeletedException;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private final RoomRepository roomRepository;
    private final HotelService hotelService;

    public Room findById(Long id) {
        Room findedRoom = roomRepository
                .findById(id)
                .orElseThrow(
                        () -> new ObjectNotFoundException(id, Room.class.getName())
                );
        if (findedRoom.isDeleted()) {
            throw new ObjectDeletedException(
                    "Room deleted",
                    id,
                    Room.class.getName());
        }
        return findedRoom;
    }

    public ResponseRoomData addRoom(RequestRoomCreate roomRequestDto) {
        Hotel currentHotel = hotelService.findHotelById(roomRequestDto.getHotelId());
        Room newRoom = getRoomFrom(roomRequestDto, new Room()).setHotel(currentHotel);
        return ResponseRoomData.of(roomRepository.save(newRoom));
    }

    public ResponseRoomData updateRoom(Long roomId, RequestRoomCreate roomRequestDto) {
        Room roomToUpdate = findById(roomId);
        Room newRoom = getRoomFrom(roomRequestDto, roomToUpdate);
        return ResponseRoomData.of(roomRepository.save(newRoom));
    }

    private Room getRoomFrom(RequestRoomCreate from, Room initialRoom) {
        return initialRoom
                .setRoomName(from.getRoomName())
                .setRoomPrice(from.getRoomPrice())
                .setRoomCapacity(from.getRoomMaxCapacity())
                .setAmenities(from.getAmenitiesList() == null
                        ? Collections.emptyList()
                        : from.getAmenitiesList()
                );
    }

    public boolean deleteRoom(Long roomId) {
        Integer res = roomRepository.updateRoomByIdAndDeleted(roomId, true);
        System.out.println(res);
        return true;
    }

}
