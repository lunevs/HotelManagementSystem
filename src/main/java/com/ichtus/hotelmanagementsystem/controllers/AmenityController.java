package com.ichtus.hotelmanagementsystem.controllers;

import com.ichtus.hotelmanagementsystem.model.anotations.IsAdministrator;
import com.ichtus.hotelmanagementsystem.model.anotations.IsUser;
import com.ichtus.hotelmanagementsystem.model.dto.amenity.RequestAmenityChange;
import com.ichtus.hotelmanagementsystem.services.AmenityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/amenities")
public class AmenityController {

    private final AmenityService amenityService;

    @GetMapping
    @IsUser
    public ResponseEntity<?> getAllAmenities() {
        return ResponseEntity.ok(amenityService.getAll());
    }

    @PostMapping
    @IsAdministrator
    public ResponseEntity<?> createAmenity(@RequestBody RequestAmenityChange amenityRequest) {
        return ResponseEntity.ok(amenityService.createAmenity(amenityRequest));

    }

    @PutMapping("{id}")
    @IsAdministrator
    public ResponseEntity<?> updateAmenity(@RequestBody RequestAmenityChange amenityRequest, @PathVariable Long id) {
        return ResponseEntity.ok(amenityService.updateAmenity(id, amenityRequest));
    }
}
