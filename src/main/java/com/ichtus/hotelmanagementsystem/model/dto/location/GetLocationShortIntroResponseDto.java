package com.ichtus.hotelmanagementsystem.model.dto.location;

import com.ichtus.hotelmanagementsystem.model.entities.Location;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GetLocationShortIntroResponseDto {

    long id;
    String locationName;
    String locationDescription;

    public static GetLocationShortIntroResponseDto of(Location loc) {
        return new GetLocationShortIntroResponseDto()
                .setId(loc.getId())
                .setLocationName(loc.getLocationName())
                .setLocationDescription(loc.getLocationDescription());
    }
}
