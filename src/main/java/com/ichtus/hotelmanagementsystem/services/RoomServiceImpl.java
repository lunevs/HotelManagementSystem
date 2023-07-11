package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.domain.Hotel;
import com.ichtus.hotelmanagementsystem.domain.Room;
import com.ichtus.hotelmanagementsystem.dto.RoomDto;
import com.ichtus.hotelmanagementsystem.repository.HotelRepository;
import com.ichtus.hotelmanagementsystem.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    @Override
    public Room saveNewRoom(RoomDto roomDTO) {
        Hotel hotel = hotelRepository.findByTitle(roomDTO.getHotelTitle()).orElseThrow();
        Room room = new Room(
                null,
                roomDTO.getRoomTitle(),
                roomDTO.getPrice(),
                roomDTO.getNumOfBeds(),
                false,
                hotel
        );
        return roomRepository.save(room);
    }

    @Override
    public Iterable<Room> findAll(Long hotelId) {
        return (hotelId == null)
                ? roomRepository.findAll()
                : roomRepository.findAllByHotelId(hotelId);
    }

}
