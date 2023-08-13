package com.ichtus.hotelmanagementsystem.repository;

import com.ichtus.hotelmanagementsystem.model.dao.Booking;
import org.springframework.data.repository.CrudRepository;

public interface BookingRepository extends CrudRepository<Booking, Long> {
}
