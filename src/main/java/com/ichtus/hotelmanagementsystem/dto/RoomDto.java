package com.ichtus.hotelmanagementsystem.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class RoomDto {

    String roomTitle;
    String hotelTitle;
    BigDecimal price;
    int numOfBeds;
}
