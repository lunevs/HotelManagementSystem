package com.ichtus.hotelmanagementsystem.repository;

import com.ichtus.hotelmanagementsystem.model.entities.Room;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {

    // find  rooms with capacity more or equal getNeededCapacity
    // and with room price between min and max
    List<Room> findAllByRoomCapacityGreaterThanEqualAndRoomPriceBetweenAndDeleted(
            int roomCapacity,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            boolean deleted
    );

    List<Room> findAllByRoomCapacityGreaterThanEqualAndRoomPriceGreaterThanEqualAndDeleted(
            int roomCapacity,
            BigDecimal minPrice,
            boolean deleted
    );

    @Modifying
    @Transactional
    @Query("update Room r set r.deleted = ?2 where r.id = ?1 ")
    Integer updateRoomByIdAndDeleted(Long id, boolean deleted);

}
