package com.ichtus.hotelmanagementsystem.model.dto.amenity;

import com.ichtus.hotelmanagementsystem.model.dictionaries.AmenityType;
import lombok.Value;

@Value
public class ChangeAmenityRequest {

    String amenityName;
    AmenityType amenityType;
    float amenityPrice;
    String amenityDescription;

}
