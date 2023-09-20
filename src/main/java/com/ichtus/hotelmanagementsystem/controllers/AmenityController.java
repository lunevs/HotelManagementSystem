package com.ichtus.hotelmanagementsystem.controllers;

import com.ichtus.hotelmanagementsystem.model.anotations.IsAdministrator;
import com.ichtus.hotelmanagementsystem.model.anotations.IsUser;
import com.ichtus.hotelmanagementsystem.model.dto.amenity.RequestAmenityChange;
import com.ichtus.hotelmanagementsystem.model.entities.Amenity;
import com.ichtus.hotelmanagementsystem.services.AmenityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller allows to manage Amenities
 *
 * @author smlunev
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/amenities")
public class AmenityController {

    private final AmenityService amenityService;

    /**
     * Endpoint to get all Amenities list
     * @return Amenities list
     */
    @GetMapping
    @IsUser
    public ResponseEntity<List<Amenity>> getAllAmenities() {
        return ResponseEntity.ok(amenityService.getAll());
    }

    /**
     * Endpoint to create new Amenity
     * @param amenityRequest dto with new Amenity parameters
     * @return Amenity - created amenity data
     */
    @PostMapping
    @IsAdministrator
    public ResponseEntity<Amenity> createAmenity(@Valid @RequestBody RequestAmenityChange amenityRequest) {
        return ResponseEntity.ok(amenityService.createAmenity(amenityRequest));

    }

    /**
     * Endpoint to update Amenity parameters
     * @param amenityRequest dto with parameters Amenity to be updated
     * @param id updated Amenity id
     * @return Amenity - updated amenity data
     */
    @PutMapping("{id}")
    @IsAdministrator
    public ResponseEntity<Amenity> updateAmenity(@Valid @RequestBody RequestAmenityChange amenityRequest, @PathVariable Long id) {
        return ResponseEntity.ok(amenityService.updateAmenity(id, amenityRequest));
    }

    /**
     * Endpoint to delete amenity
     * @param id deleted amenity id
     * @return empty response with status 200
     */
    @DeleteMapping("{id}")
    @IsAdministrator
    public ResponseEntity<?> deleteAmenity(@PathVariable Long id) {
        amenityService.deleteAmenity(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
