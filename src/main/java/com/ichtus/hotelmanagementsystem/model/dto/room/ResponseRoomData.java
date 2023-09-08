package com.ichtus.hotelmanagementsystem.model.dto.room;

import com.ichtus.hotelmanagementsystem.model.entities.Room;
import com.ichtus.hotelmanagementsystem.model.dto.amenity.ResponseAmenityData;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Data
@Accessors(chain = true)
public class ResponseRoomData {
    long id;
    long hotelId;
    int roomCapacity;
    String roomName;
    BigDecimal roomPrice;
    List<ResponseAmenityData> amenities;

    public static ResponseRoomData of(Room room) {
        return new ResponseRoomData()
                .setId(room.getId())
                .setRoomName(room.getRoomName())
                .setRoomPrice(room.getRoomPrice())
                .setRoomCapacity(room.getRoomCapacity())
                .setHotelId(room.getHotel().getId())
                .setAmenities(
                        room.getAmenities() == null
                                ? Collections.emptyList()
                                : room.getAmenities().stream().map(ResponseAmenityData::of).toList()
                );
    }
}
