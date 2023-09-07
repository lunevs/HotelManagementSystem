package com.ichtus.hotelmanagementsystem.model.dto.amenity;

import com.ichtus.hotelmanagementsystem.model.dictionaries.AmenityType;
import lombok.Value;

@Value
public class RequestAmenityChange {

    String amenityName;
    String amenityDescription;
    float amenityPrice;
    AmenityType amenityType;

}
