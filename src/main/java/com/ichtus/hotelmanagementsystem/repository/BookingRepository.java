package com.ichtus.hotelmanagementsystem.repository;

import com.ichtus.hotelmanagementsystem.model.entities.Account;
import com.ichtus.hotelmanagementsystem.model.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("select b from Booking b where (b.startDate between ?1 and ?2 or b.endDate between ?1 and ?2) " +
            "or (b.startDate < ?1 and b.endDate > ?2)")
    List<Booking> findAllByParameters(Date d1, Date d2);


    @Query("select b from Booking b where b.room.id = ?1 and b.deleted=false and (" +
            "(b.startDate between ?2 and ?3 or b.endDate between ?2 and ?3) " +
            "or (b.startDate < ?2 and b.endDate > ?3)" +
            ")")
    List<Booking> checkBookingsForRoom(Long roomId, Date d1, Date d2);

    @Query("select b from Booking b where b.deleted = ?1 and b.room.hotel.deleted = false")
    List<Booking> findAllByDeleted(Boolean deleted);

    @Query("select b from Booking b where b.room.hotel.id = ?1")
    List<Booking> findAllByHotelId(Long id);

}
