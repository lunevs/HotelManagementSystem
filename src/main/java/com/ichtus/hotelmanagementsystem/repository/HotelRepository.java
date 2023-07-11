package com.ichtus.hotelmanagementsystem.repository;

import com.ichtus.hotelmanagementsystem.domain.Hotel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface HotelRepository extends CrudRepository<Hotel, Long> {

    Optional<Hotel> findByTitle(String title);

    Optional<Hotel> updateHotelBy(Long id);

}
