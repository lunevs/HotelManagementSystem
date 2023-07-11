package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.domain.Hotel;
import com.ichtus.hotelmanagementsystem.dto.HotelDto;
import com.ichtus.hotelmanagementsystem.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    @Override
    public Hotel saveNewHotel(HotelDto hotelDto) {
        Hotel hotel = new Hotel(
                null,
                hotelDto.getTitle(),
                hotelDto.getDescription()
        );
        return hotelRepository.save(hotel);
    }

    @Override
    public Iterable<Hotel> findAll() {
        return hotelRepository.findAll();
    }
}
