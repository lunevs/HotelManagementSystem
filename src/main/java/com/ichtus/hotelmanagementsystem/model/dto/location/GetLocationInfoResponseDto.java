package com.ichtus.hotelmanagementsystem.model.dto.location;

import com.ichtus.hotelmanagementsystem.model.dao.Amenity;
import com.ichtus.hotelmanagementsystem.model.dao.Location;
import com.ichtus.hotelmanagementsystem.model.dao.Room;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Set;

@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetLocationInfoResponseDto {

    long id;
    String locationName;
    String locationDescription;
    Set<Room> roomsList;
    Set<Amenity> amenities;

    public static GetLocationInfoResponseDto of(Location loc) {
        return new GetLocationInfoResponseDto()
                .setId(loc.getId())
                .setLocationName(loc.getLocationName())
                .setLocationDescription(loc.getLocationDescription())
                .setRoomsList(loc.getRoomsList())
                .setAmenities(loc.getAmenities());
    }
}
