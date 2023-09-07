package com.ichtus.hotelmanagementsystem.model.dto.booking;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class RequestNewBooking {

    private long roomId;
    private long locationId;
    private int numberOfGuests;
    private Date startDate;
    private Date endDate;

}
