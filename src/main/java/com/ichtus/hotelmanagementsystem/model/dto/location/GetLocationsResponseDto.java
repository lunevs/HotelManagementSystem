package com.ichtus.hotelmanagementsystem.model.dto.location;

import com.ichtus.hotelmanagementsystem.model.entities.Location;
import com.ichtus.hotelmanagementsystem.model.dto.room.GetRoomShortIntroResponseDto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
public class GetLocationsResponseDto {

    long id;
    String locationName;
    String locationDescription;
    Set<GetRoomShortIntroResponseDto> roomsList;

    public static GetLocationsResponseDto of (Location loc) {
        return new GetLocationsResponseDto()
                .setId(loc.getId())
                .setLocationName(loc.getLocationName())
                .setLocationDescription(loc.getLocationDescription())
                .setRoomsList(
                        loc.getRoomsList().stream()
                                .map(GetRoomShortIntroResponseDto::of)
                                .collect(Collectors.toSet())
                );
    }

}
