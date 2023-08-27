package com.ichtus.hotelmanagementsystem.model.dto.location;

import com.ichtus.hotelmanagementsystem.model.entities.Location;
import lombok.Value;

@Value
public class CreateLocationResponseDto {
    long id;
    String locationName;
    String locationDescription;

    public static CreateLocationResponseDto of(Location location) {
        return new CreateLocationResponseDto(
                location.getId(),
                location.getLocationName(),
                location.getLocationDescription()
        );
    }
}
