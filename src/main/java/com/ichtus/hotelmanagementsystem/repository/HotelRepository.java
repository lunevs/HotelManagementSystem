package com.ichtus.hotelmanagementsystem.repository;

import com.ichtus.hotelmanagementsystem.model.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    List<Hotel> findAllByDeleted(boolean deleted);

    List<Hotel> findAllByHotelCity(String hotelCity);
}
