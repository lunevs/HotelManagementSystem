package com.ichtus.hotelmanagementsystem.controllers;

import com.ichtus.hotelmanagementsystem.model.dao.Location;
import com.ichtus.hotelmanagementsystem.model.dto.location.CreateLocationRequestDto;
import com.ichtus.hotelmanagementsystem.model.dto.room.CreateRoomRequestDto;
import com.ichtus.hotelmanagementsystem.model.dto.room.GetRoomsResponseDto;
import com.ichtus.hotelmanagementsystem.model.dto.location.UpdateLocationRequestDto;
import com.ichtus.hotelmanagementsystem.services.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/locations")
@RequiredArgsConstructor
@Slf4j
public class LocationController {

    private final LocationService locationService;

    @GetMapping
    ResponseEntity<Iterable<Location>> getLocationsList() {
        return new ResponseEntity<>(locationService.getLocationsList(), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<?> createLocation(@Valid @RequestBody CreateLocationRequestDto locationDto) {
        Location savedLocation = locationService.addLocation(locationDto);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI savedLocationUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedLocation.getId())
                .toUri();
        responseHeaders.setLocation(savedLocationUri);
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getLocationInfo(@PathVariable Long id) {
        return new ResponseEntity<>(locationService.getLocationInfo(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateLocationInfo(@PathVariable Long id, @RequestBody UpdateLocationRequestDto locationRequestDto) {
        locationService.updateLocationInfo(id, locationRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/rooms")
    ResponseEntity<Iterable<GetRoomsResponseDto>> getLocationRoomsList(@PathVariable Long id) {
        Location currentLocation = locationService.getLocationInfo(id);
        return new ResponseEntity<>(locationService.getRoomsList(currentLocation), HttpStatus.OK);
    }

    @PostMapping("/{id}/rooms")
    ResponseEntity<?> addRoomToLocation(@PathVariable Long id, @Valid @RequestBody CreateRoomRequestDto roomRequestDto) {
        return new ResponseEntity<>(locationService.addRoomToLocation(id, roomRequestDto), HttpStatus.OK);
    }
}
