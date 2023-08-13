package com.ichtus.hotelmanagementsystem.model.dto.room;

import com.ichtus.hotelmanagementsystem.model.dao.Room;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class GetRoomShortIntroResponseDto {

    long id;
    String roomName;
    BigDecimal roomPrice;
    int roomCapacity;
    long locationId;

    public static GetRoomShortIntroResponseDto of(Room room) {
        return new GetRoomShortIntroResponseDto()
                .setId(room.getId())
                .setRoomName(room.getRoomName())
                .setRoomPrice(room.getRoomPrice())
                .setRoomCapacity(room.getRoomCapacity())
                .setLocationId(room.getLocation().getId());
    }
}
