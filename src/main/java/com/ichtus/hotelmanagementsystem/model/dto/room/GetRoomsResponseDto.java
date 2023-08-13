package com.ichtus.hotelmanagementsystem.model.dto.room;

import com.ichtus.hotelmanagementsystem.model.dao.Room;
import com.ichtus.hotelmanagementsystem.model.dto.amenity.GetRoomAmenitiesResponseDto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
public class GetRoomsResponseDto {
    long id;
    String roomName;
    BigDecimal roomPrice;
    int roomCapacity;
    long locationId;
    Set<GetRoomAmenitiesResponseDto> amenities;

    public static GetRoomsResponseDto of(Room room) {
        return new GetRoomsResponseDto()
                .setId(room.getId())
                .setRoomName(room.getRoomName())
                .setRoomPrice(room.getRoomPrice())
                .setRoomCapacity(room.getRoomCapacity())
                .setLocationId(room.getLocation().getId())
                .setAmenities(room.getAmenities().stream()
                        .map(GetRoomAmenitiesResponseDto::of)
                        .collect(Collectors.toSet())
                );
    }
}
