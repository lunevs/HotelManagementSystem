package com.ichtus.hotelmanagementsystem.model.dto.location;

import com.ichtus.hotelmanagementsystem.model.dto.room.GetRoomsResponseDto;
import com.ichtus.hotelmanagementsystem.model.entities.Amenity;
import com.ichtus.hotelmanagementsystem.model.entities.Location;
import com.ichtus.hotelmanagementsystem.model.entities.Room;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Set;

@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetLocationInfoResponseDto {

    long id;
    String locationName;
    String locationDescription;
    List<GetRoomsResponseDto> roomsList;
    List<Amenity> amenities;

    public static GetLocationInfoResponseDto of(Location loc) {
        return new GetLocationInfoResponseDto()
                .setId(loc.getId())
                .setLocationName(loc.getLocationName())
                .setLocationDescription(loc.getLocationDescription())
                .setRoomsList(loc.getRoomsList().stream().map(GetRoomsResponseDto::of).toList())
                .setAmenities(loc.getAmenities());
    }
}

