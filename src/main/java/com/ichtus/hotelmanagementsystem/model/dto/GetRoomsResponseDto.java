package com.ichtus.hotelmanagementsystem.model.dto;

import lombok.Value;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Value
@Accessors(chain = true)
public class GetRoomsResponseDto {
    long id;
    String roomName;
    BigDecimal roomPrice;
    int roomCapacity;
    long locationId;
}
