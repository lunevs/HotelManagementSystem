package com.ichtus.hotelmanagementsystem.model.dto.booking;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class RequestNewBooking {

    @NotBlank
    private long roomId;

    @NotBlank
    private long hotelId;

    private int numberOfGuests;

    @NotNull
    @Future
    private Date startDate;

    @NotNull
    @Future
    private Date endDate;

}
