package com.ichtus.hotelmanagementsystem.controllers;

import com.ichtus.hotelmanagementsystem.model.anotations.IsModerator;
import com.ichtus.hotelmanagementsystem.model.anotations.IsUser;
import com.ichtus.hotelmanagementsystem.model.entities.Hotel;
import com.ichtus.hotelmanagementsystem.model.dto.hotels.ResponseHotelChange;
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

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
@Slf4j
public class LocationController {

    private final HotelService hotelService;

    @GetMapping
    @IsUser
    ResponseEntity<Iterable<ResponseHotelChange>> getLocationsList() {
        return new ResponseEntity<>(hotelService.getLocationsList(), HttpStatus.OK);
    }

    @PostMapping
    @IsModerator
    ResponseEntity<?> createLocation(@Valid @RequestBody RequestHotelChange hotelChange, Principal principal) {
        Hotel savedHotel = hotelService.addLocation(hotelChange, principal.getName());
        HttpHeaders responseHeaders = new HttpHeaders();
        URI savedLocationUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedHotel.getId())
                .toUri();
        responseHeaders.setLocation(savedLocationUri);
        return new ResponseEntity<>(
                ResponseHotelChange.of(savedHotel),
                responseHeaders,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    @IsUser
    ResponseEntity<?> getLocationInfo(@PathVariable Long id) {
        return ResponseEntity.ok(hotelService.getLocationInfo(id));
    }

    @PutMapping("/{id}")
    @IsModerator
    ResponseEntity<?> updateLocationInfo(@PathVariable Long id, @RequestBody RequestHotelChange locationRequestDto) {
        return ResponseEntity.ok(hotelService.updateLocationInfo(id, locationRequestDto));
    }

    @DeleteMapping("/{id}")
    @IsModerator
    ResponseEntity<?> deleteLocation(@PathVariable Long id) {
        hotelService.deleteLocation(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/rooms")
    @IsUser
    ResponseEntity<Iterable<ResponseRoomData>> getLocationRoomsList(@PathVariable Long id) {
        return new ResponseEntity<>(hotelService.getRoomsList(id), HttpStatus.OK);
    }

}
