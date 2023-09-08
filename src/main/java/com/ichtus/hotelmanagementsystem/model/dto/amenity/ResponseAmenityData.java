package com.ichtus.hotelmanagementsystem.model.dto.amenity;

import com.ichtus.hotelmanagementsystem.model.dictionaries.AmenityType;
import com.ichtus.hotelmanagementsystem.model.entities.Amenity;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResponseAmenityData {

    Long id;
    String amenityName;
    float amenityPrice;
    AmenityType amenityType;

    public static ResponseAmenityData of(Amenity amenity) {
        return new ResponseAmenityData()
                .setId(amenity.getId())
                .setAmenityName(amenity.getAmenityName())
                .setAmenityPrice(amenity.getAmenityPrice())
                .setAmenityType(amenity.getAmenityType());
    }
}
