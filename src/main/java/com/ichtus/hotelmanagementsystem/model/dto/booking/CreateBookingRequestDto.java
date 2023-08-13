package com.ichtus.hotelmanagementsystem.model.dto.booking;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class CreateBookingRequestDto {

    private long roomId;
    private long locationId;
    private LocalDateTime startDate;
    private long numberOfNights;
    private int numberOfGuests;

}
