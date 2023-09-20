package com.ichtus.hotelmanagementsystem.controllers;

import com.ichtus.hotelmanagementsystem.model.anotations.IsModerator;
import com.ichtus.hotelmanagementsystem.model.anotations.IsUser;
import com.ichtus.hotelmanagementsystem.model.dto.hotels.ResponseHotelData;
import com.ichtus.hotelmanagementsystem.model.dto.room.ResponseRoomData;
import com.ichtus.hotelmanagementsystem.model.dto.hotels.RequestHotelChange;
import com.ichtus.hotelmanagementsystem.services.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;

/**
 * Controller allows to manage Hotels
 * @author smlunev
 */
@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    /**
     * Endpoint to get all non deleted hotels
     * @return list ResponseHotelData with hotels information
     */
    @GetMapping
    @IsUser
    public ResponseEntity<List<ResponseHotelData>> getHotelsList() {
        return new ResponseEntity<>(hotelService.getHotelsList(), HttpStatus.OK);
    }

    /**
     * Endpoint to get all cities with our Hotels
     * @return set with cities names
     */
    @GetMapping("/cities")
    @IsUser
    public ResponseEntity<Set<String>> getHotelsCities() {
        return new ResponseEntity<>(hotelService.getHotelsCities(), HttpStatus.OK);
    }

    /**
     * Endpoint to create new Hotel
     * @param hotelChange dto with new hotel information
     * @param principal authenticated user information
     * @return ResponseHotelData with created hotel information
     */
    @PostMapping
    @IsModerator
    public ResponseEntity<ResponseHotelData> createHotel(@Valid @RequestBody RequestHotelChange hotelChange, Principal principal) {
        ResponseHotelData savedHotel = hotelService.addHotel(hotelChange, principal.getName());
        return new ResponseEntity<>(savedHotel, HttpStatus.CREATED);
    }

    /**
     * Endpoint to get detailed hotel information
     * @param id searched hotel id
     * @return ResponseHotelData with hotel information
     */
    @GetMapping("/{id}")
    @IsUser
    public ResponseEntity<ResponseHotelData> getHotelInfo(@PathVariable Long id) {
        return ResponseEntity.ok(hotelService.getHotelInfo(id));
    }

    /**
     * Endpoint to update common hotel information
     * @param id updated hotel id
     * @param hotelRequestDto dto with hotel information for update
     * @return ResponseHotelData with updated hotel data
     */
    @PutMapping("/{id}")
    @IsModerator
    public ResponseEntity<ResponseHotelData> updateHotelInfo(@PathVariable Long id, @Valid @RequestBody RequestHotelChange hotelRequestDto) {
        return ResponseEntity.ok(hotelService.updateHotelInfo(id, hotelRequestDto));
    }

    /**
     * Endpoint to delete hotel
     * @param id deleted hotel id
     * @return empty response with status 200
     */
    @DeleteMapping("/{id}")
    @IsModerator
    public ResponseEntity<?> deleteHotel(@PathVariable Long id) {
        if (hotelService.deleteHotel(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint to get all rooms information in one Hotel
     * @param id searched hotel id
     * @return list of ResponseRoomData with rooms information
     */
    @GetMapping("/{id}/rooms")
    @IsUser
    public ResponseEntity<List<ResponseRoomData>> getHotelRoomsList(@PathVariable Long id) {
        return new ResponseEntity<>(hotelService.getRoomsList(id), HttpStatus.OK);
    }

}
