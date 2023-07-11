package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.domain.Room;
import com.ichtus.hotelmanagementsystem.dto.RoomDto;

import java.util.Optional;


public interface RoomService {

    Room saveNewRoom(RoomDto roomDTO);

    Iterable<Room> findAll(Long hotelId);
}
