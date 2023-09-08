package com.ichtus.hotelmanagementsystem.controllers;

import com.ichtus.hotelmanagementsystem.model.anotations.IsModerator;
import com.ichtus.hotelmanagementsystem.model.anotations.IsUser;
import com.ichtus.hotelmanagementsystem.model.entities.Hotel;
import com.ichtus.hotelmanagementsystem.model.dto.hotels.ResponseHotelData;
import com.ichtus.hotelmanagementsystem.model.dto.room.ResponseRoomData;
import com.ichtus.hotelmanagementsystem.model.dto.hotels.RequestHotelChange;
import com.ichtus.hotelmanagementsystem.services.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
@Slf4j
public class HotelController {

    private final HotelService hotelService;

    @GetMapping
    @IsUser
    public ResponseEntity<?> getHotelsList() {
        return new ResponseEntity<>(hotelService.getHotelsList(), HttpStatus.OK);
    }

    @GetMapping("/cities")
    @IsUser
    public ResponseEntity<?> getHotelsCities() {
        return new ResponseEntity<>(hotelService.getHotelsCities(), HttpStatus.OK);
    }

    @PostMapping
    @IsModerator
    public ResponseEntity<ResponseHotelData> createHotel(@Valid @RequestBody RequestHotelChange hotelChange, Principal principal) {
        Hotel savedHotel = hotelService.addHotel(hotelChange, principal.getName());
        HttpHeaders responseHeaders = new HttpHeaders();
        URI savedHotelUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedHotel.getId())
                .toUri();
        responseHeaders.setLocation(savedHotelUri);
        return new ResponseEntity<>(
                ResponseHotelData.of(savedHotel),
                responseHeaders,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    @IsUser
    public ResponseEntity<ResponseHotelData> getHotelInfo(@PathVariable Long id) {
        return ResponseEntity.ok(hotelService.getHotelInfo(id));
    }

    @PutMapping("/{id}")
    @IsModerator
    public ResponseEntity<ResponseHotelData> updateHotelInfo(@PathVariable Long id, @RequestBody RequestHotelChange hotelRequestDto) {
        return ResponseEntity.ok(hotelService.updateHotelInfo(id, hotelRequestDto));
    }

    @DeleteMapping("/{id}")
    @IsModerator
    public ResponseEntity<?> deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/rooms")
    @IsUser
    public ResponseEntity<List<ResponseRoomData>> getHotelRoomsList(@PathVariable Long id) {
        return new ResponseEntity<>(hotelService.getRoomsList(id), HttpStatus.OK);
    }

}
