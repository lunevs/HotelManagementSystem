package com.ichtus.hotelmanagementsystem.model.dto.amenity;

import com.ichtus.hotelmanagementsystem.model.dict.AmenityUpdateActionType;
import lombok.Value;

@Value
public class UpdateRoomAmenitiesRequestDto {

    long amenityId;
    AmenityUpdateActionType actionType;
}
