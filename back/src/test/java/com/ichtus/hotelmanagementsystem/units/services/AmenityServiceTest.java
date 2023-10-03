package com.ichtus.hotelmanagementsystem.units.services;

import com.ichtus.hotelmanagementsystem.model.dictionaries.AmenityType;
import com.ichtus.hotelmanagementsystem.model.dto.amenity.RequestAmenityChange;
import com.ichtus.hotelmanagementsystem.model.entities.Amenity;
import com.ichtus.hotelmanagementsystem.repository.AmenityRepository;
import com.ichtus.hotelmanagementsystem.services.AmenityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AmenityServiceTest {

    @Autowired
    private AmenityService amenityService;

    @MockBean
    private AmenityRepository amenityRepository;

    private final Amenity baseAmenity = new Amenity()
            .setId(0)
            .setAmenityName("some test Amenity")
            .setAmenityDescription("")
            .setAmenityPrice(0)
            .setAmenityType(AmenityType.HOTEL);
    private final RequestAmenityChange amenityChange = new RequestAmenityChange(
            baseAmenity.getAmenityName(),
            baseAmenity.getAmenityDescription(),
            baseAmenity.getAmenityPrice(),
            baseAmenity.getAmenityType()
    );

    @Test
    void whenGetAll() {
        given(amenityRepository.findAll()).willReturn(Collections.singletonList(baseAmenity));
        List<Amenity> amenityList = amenityService.getAll();
        assertThat(amenityList).hasSize(1);
        assertThat(amenityList.get(0).getAmenityName()).isEqualTo(baseAmenity.getAmenityName());
    }

    @Test
    void  whenCreateAmenity() {
        given(amenityRepository.save(any())).willReturn(baseAmenity);
        Amenity amenity = amenityService.createAmenity(amenityChange);
        assertThat(amenity.getAmenityName()).isEqualTo(amenityChange.getAmenityName());
    }

    @Test
    void  whenUpdateAmenity() {
        given(amenityRepository.findById(any())).willReturn(Optional.of(baseAmenity));
        given(amenityRepository.save(any())).willReturn(baseAmenity);
        Amenity amenity = amenityService.updateAmenity(0L, amenityChange);
        assertThat(amenity.getAmenityName()).isEqualTo(amenityChange.getAmenityName());
    }

}
