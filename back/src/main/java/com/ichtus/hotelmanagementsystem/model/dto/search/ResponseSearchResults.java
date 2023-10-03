package com.ichtus.hotelmanagementsystem.model.dto.search;

import com.ichtus.hotelmanagementsystem.model.dto.amenity.ResponseAmenityData;
import com.ichtus.hotelmanagementsystem.model.dto.room.ResponseRoomData;
import com.ichtus.hotelmanagementsystem.model.entities.Hotel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ResponseSearchResults {

    long hotelId;
    String hotelName;
    String hotelDescription;
    String hotelCity;

    List<ResponseRoomData> freeRoomsList;
    List<ResponseAmenityData> hotelAmenities;

    public static ResponseSearchResults of(Hotel hotel) {
        return new ResponseSearchResults()
                .setHotelId(hotel.getId())
                .setHotelName(hotel.getHotelName())
                .setHotelDescription(hotel.getHotelDescription())
                .setHotelCity(hotel.getHotelCity())
                .setFreeRoomsList(hotel.getRoomsList().stream().map(ResponseRoomData::of).toList())
                .setHotelAmenities(hotel.getAmenities().stream().map(ResponseAmenityData::of).toList());
    }

}
