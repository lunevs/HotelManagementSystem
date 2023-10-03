package com.ichtus.hotelmanagementsystem.model.dto.hotels;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Value;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RequestHotelChange {

    @NotBlank
    @Size(min = 5)
    String hotelName;

    @NotBlank
    @Size(min = 20)
    String hotelDescription;

    @NotBlank
    @Size(min = 5)
    String hotelCity;
}
