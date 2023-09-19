package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.model.dto.amenity.RequestAmenityChange;
import com.ichtus.hotelmanagementsystem.model.entities.Amenity;
import com.ichtus.hotelmanagementsystem.repository.AmenityRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AmenityService {

    private final AmenityRepository amenityRepository;

    public List<Amenity> getAll() {
        return amenityRepository.findAll();
    }

    public Amenity createAmenity(RequestAmenityChange amenityRequest) {
        Amenity amenity = new Amenity()
                .setAmenityName(amenityRequest.getAmenityName())
                .setAmenityDescription(amenityRequest.getAmenityDescription())
                .setAmenityType(amenityRequest.getAmenityType())
                .setAmenityPrice(amenityRequest.getAmenityPrice());
        return amenityRepository.save(amenity);

    }

    public Amenity updateAmenity(Long id, RequestAmenityChange amenityRequest) {
        Amenity amenity = amenityRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, Amenity.class.getName()))
                .setAmenityName(amenityRequest.getAmenityName())
                .setAmenityDescription(amenityRequest.getAmenityDescription())
                .setAmenityPrice(amenityRequest.getAmenityPrice())
                .setAmenityType(amenityRequest.getAmenityType());
        return amenityRepository.save(amenity);
    }
}
