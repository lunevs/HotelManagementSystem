package com.ichtus.hotelmanagementsystem.model.dto.hotels;

import lombok.Value;

@Value
public class RequestHotelChange {

    String hotelName;
    String hotelDescription;
    String hotelCity;
}
