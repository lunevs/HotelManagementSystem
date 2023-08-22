package com.ichtus.hotelmanagementsystem.model.dto.amenity;

import com.ichtus.hotelmanagementsystem.model.dictionaries.AmenityUpdateActionType;
import lombok.Value;

@Value
public class UpdateLocationAmenitiesRequestDto {

    long amenityId;
    AmenityUpdateActionType actionType;
}
