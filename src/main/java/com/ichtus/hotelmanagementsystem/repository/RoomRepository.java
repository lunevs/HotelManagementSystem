package com.ichtus.hotelmanagementsystem.repository;

import com.ichtus.hotelmanagementsystem.model.dao.Location;
import com.ichtus.hotelmanagementsystem.model.dao.Room;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


public interface RoomRepository extends CrudRepository<Room, Long> {

//    @Query("select r from Room r where r.location = ?1")
//    Iterable<Room> searchRoomsBy(Location location);

//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query("delete from Room r where r.location = ?1")
//    void deleteAllBy(Location location);

}
