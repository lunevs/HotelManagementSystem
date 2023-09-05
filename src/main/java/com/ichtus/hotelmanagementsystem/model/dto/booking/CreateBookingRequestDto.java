package com.ichtus.hotelmanagementsystem.model.dto.booking;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Accessors(chain = true)
public class CreateBookingRequestDto {

    private long roomId;
    private long locationId;
    private Date startDate;
    private Date endDate;
    private int numberOfGuests;

}
