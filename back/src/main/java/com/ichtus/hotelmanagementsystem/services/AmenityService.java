package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.model.dto.amenity.RequestAmenityChange;
import com.ichtus.hotelmanagementsystem.model.entities.Amenity;
import com.ichtus.hotelmanagementsystem.repository.AmenityRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Defines services to interact with hotel's and room's amenities
 * @author smlunev
 */
@Service
@RequiredArgsConstructor
public class AmenityService {

    private final AmenityRepository amenityRepository;

    /**
     * Get all amenities list
     * @return list of amenities
     */
    public List<Amenity> getAll() {
        return amenityRepository.findAll();
    }

    /**
     * Create new amenity
     * @param amenityRequest dto with new amenity data
     * @return created amenity
     */
    public Amenity createAmenity(RequestAmenityChange amenityRequest) {
        Amenity amenity = new Amenity()
                .setAmenityName(amenityRequest.getAmenityName())
                .setAmenityDescription(amenityRequest.getAmenityDescription())
                .setAmenityType(amenityRequest.getAmenityType())
                .setAmenityPrice(amenityRequest.getAmenityPrice());
        return amenityRepository.save(amenity);

    }

    /**
     * update amenity common information
     * @param id updated amenity id
     * @param amenityRequest dto with new amenity data
     * @return updated amenity
     */
    public Amenity updateAmenity(Long id, RequestAmenityChange amenityRequest) {
        Amenity amenity = amenityRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, Amenity.class.getName()))
                .setAmenityName(amenityRequest.getAmenityName())
                .setAmenityDescription(amenityRequest.getAmenityDescription())
                .setAmenityPrice(amenityRequest.getAmenityPrice())
                .setAmenityType(amenityRequest.getAmenityType());
        return amenityRepository.save(amenity);
    }

    /**
     * delete current amenity
     * @param id deleted amenity id
     */
    public void deleteAmenity(Long id) {
        amenityRepository.deleteById(id);
    }
}
