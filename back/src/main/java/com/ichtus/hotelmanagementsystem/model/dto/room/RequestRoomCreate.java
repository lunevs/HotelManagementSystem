package com.ichtus.hotelmanagementsystem.model.dto.room;

import com.ichtus.hotelmanagementsystem.model.anotations.ValidRoomCapacity;
import com.ichtus.hotelmanagementsystem.model.entities.Amenity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
public class RequestRoomCreate {

    @Positive
    Long hotelId;

    @NotBlank
    @Size(min = 5)
    String roomName;

    @Positive
    BigDecimal roomPrice;

    @ValidRoomCapacity
    Integer roomMaxCapacity;

    List<Amenity> amenitiesList;
}
