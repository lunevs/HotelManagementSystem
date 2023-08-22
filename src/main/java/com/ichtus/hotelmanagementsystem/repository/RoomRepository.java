package com.ichtus.hotelmanagementsystem.repository;

import com.ichtus.hotelmanagementsystem.model.entities.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {

//    @Query("select r from Room r where r.location = ?1")
//    Iterable<Room> searchRoomsBy(Location location);

//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query("delete from Room r where r.location = ?1")
//    void deleteAllBy(Location location);

}
