package com.ichtus.hotelmanagementsystem.repository;

import com.ichtus.hotelmanagementsystem.domain.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoomRepository extends CrudRepository<Room, Long> {

    Optional<Room> findByTitle(String title);

    Iterable<Room> findAllByHotelId(Long hotelId);

    Optional<Room> updateRoomBy(Long id);
}
