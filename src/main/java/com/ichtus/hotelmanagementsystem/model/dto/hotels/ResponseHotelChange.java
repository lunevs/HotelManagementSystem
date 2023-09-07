package com.ichtus.hotelmanagementsystem.model.dto.hotels;

import com.ichtus.hotelmanagementsystem.model.dto.room.ResponseRoomData;
import com.ichtus.hotelmanagementsystem.model.entities.Hotel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.List;


@Data
@Accessors(chain = true)
public class ResponseHotelChange {

    long id;
    String hotelName;
    String hotelDescription;
    String hotelCity;
    String adminName;

    List<ResponseRoomData> roomsList;

    public static ResponseHotelChange of (Hotel hotel) {
        return new ResponseHotelChange()
                .setId(hotel.getId())
                .setHotelName(hotel.getHotelName())
                .setHotelDescription(hotel.getHotelDescription())
                .setAdminName(hotel.getHotelAdmin().getAccountName())
                .setHotelCity(hotel.getHotelCity())
                .setRoomsList(
                        hotel.getRoomsList() == null ? Collections.emptyList() :
                        hotel.getRoomsList().stream()
                                .map(ResponseRoomData::of)
                                .toList()
                );
    }

}
