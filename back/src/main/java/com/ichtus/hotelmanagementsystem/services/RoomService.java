package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.model.dto.room.RequestRoomCreate;
import com.ichtus.hotelmanagementsystem.model.dto.room.ResponseRoomData;
import com.ichtus.hotelmanagementsystem.model.entities.Hotel;
import com.ichtus.hotelmanagementsystem.model.entities.Room;
import com.ichtus.hotelmanagementsystem.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectDeletedException;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Defines services to interact with hotel's rooms
 * @author smlunev
 */
@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final HotelService hotelService;

    /**
     * Find room by id. If room has deleted flag method will return ObjectDeletedException
     * If room not found method will return ObjectNotFoundException
     * @param id searched id
     * @return room object
     */
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

    /**
     * Create new room and add it to the hotel
     * @param roomRequestDto new room parameters
     * @return dto ResponseRoomData with created room data
     */
    public ResponseRoomData addRoom(RequestRoomCreate roomRequestDto) {
        Hotel currentHotel = hotelService.findHotelById(roomRequestDto.getHotelId());
        Room newRoom = getRoomFrom(roomRequestDto, new Room()).setHotel(currentHotel);
        return ResponseRoomData.of(roomRepository.save(newRoom));
    }

    /**
     * Update given room
     * @param roomId updated room id
     * @param roomRequestDto room's parameters to be updated
     * @return updated room
     */
    public ResponseRoomData updateRoom(Long roomId, RequestRoomCreate roomRequestDto) {
        Room roomToUpdate = findById(roomId);
        Room newRoom = getRoomFrom(roomRequestDto, roomToUpdate);
        return ResponseRoomData.of(roomRepository.save(newRoom));
    }

    /**
     * Internal service to create new copy of room object
     * @param from object to copy
     * @param initialRoom initial object
     * @return new room object
     */
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

    /**
     * set deleted flag to given room
     * @param roomId deleted room id
     * @return true if deleted or false otherwise
     */
    public boolean deleteRoom(Long roomId) {
        return roomRepository.updateRoomByIdAndDeleted(roomId, true) == 1;
    }

}
