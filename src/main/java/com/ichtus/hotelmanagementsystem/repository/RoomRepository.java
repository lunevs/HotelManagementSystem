package com.ichtus.hotelmanagementsystem.repository;

import com.ichtus.hotelmanagementsystem.model.entities.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {

    // find  rooms with capacity more or equal getNeededCapacity
    // and with room price between min and max
    List<Room> findAllByRoomCapacityGreaterThanAndRoomPriceBetween(int roomCapacity, BigDecimal minPrice, BigDecimal maxPrice);

    List<Room> findAllByRoomCapacityGreaterThanAndRoomPriceGreaterThan(int roomCapacity, BigDecimal minPrice);

}
