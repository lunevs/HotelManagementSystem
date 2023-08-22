package com.ichtus.hotelmanagementsystem.model.dto.room;

import com.ichtus.hotelmanagementsystem.model.entities.Room;
import com.ichtus.hotelmanagementsystem.model.dto.amenity.GetRoomAmenitiesResponseDto;
import com.ichtus.hotelmanagementsystem.model.dto.location.GetLocationShortIntroResponseDto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
public class SearchRoomsResponseDto {

    long roomId;
    String roomName;
    BigDecimal roomPrice;
    int roomCapacity;
    Set<GetRoomAmenitiesResponseDto> amenities;
    GetLocationShortIntroResponseDto location;

    public static SearchRoomsResponseDto of(Room room) {
        return new SearchRoomsResponseDto()
                .setRoomId(room.getId())
                .setRoomName(room.getRoomName())
                .setRoomPrice(room.getRoomPrice())
                .setRoomCapacity(room.getRoomCapacity())
                .setAmenities(
                        room.getAmenities().stream()
                                .map(GetRoomAmenitiesResponseDto::of)
                                .collect(Collectors.toSet())
                )
                .setLocation(
                        GetLocationShortIntroResponseDto.of(room.getLocation())
                );
    }
}
