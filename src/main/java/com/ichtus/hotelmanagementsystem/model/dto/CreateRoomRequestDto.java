package com.ichtus.hotelmanagementsystem.model.dto;

import com.ichtus.hotelmanagementsystem.model.anotations.ValidRoomCapacity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class CreateRoomRequestDto {

    @NotEmpty
    String roomName;

    @Positive
    BigDecimal roomPrice;

    @ValidRoomCapacity
    Integer roomMaxCapacity;
}
