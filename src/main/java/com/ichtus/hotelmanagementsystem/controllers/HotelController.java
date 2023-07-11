package com.ichtus.hotelmanagementsystem.controllers;

import com.ichtus.hotelmanagementsystem.domain.Hotel;
import com.ichtus.hotelmanagementsystem.dto.HotelDto;
import com.ichtus.hotelmanagementsystem.services.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/hotel")
@Slf4j
public class HotelController {
    private final HotelService hotelService;

    @PostMapping
    ResponseEntity<Hotel> saveNewHotel(@RequestBody HotelDto hotelDto) {
        log.info("save new Hotel " + hotelDto.getTitle());
        return ResponseEntity.ok(hotelService.saveNewHotel(hotelDto));
    }

    @GetMapping
    ResponseEntity<Iterable<Hotel>> findAll() {
        return ResponseEntity.ok(hotelService.findAll());
    }
}
