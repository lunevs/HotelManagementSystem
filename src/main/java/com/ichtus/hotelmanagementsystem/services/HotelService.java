package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.domain.Hotel;
import com.ichtus.hotelmanagementsystem.dto.HotelDto;

public interface HotelService {

    Hotel saveNewHotel(HotelDto hotelDto);

    Iterable<Hotel> findAll();
}
