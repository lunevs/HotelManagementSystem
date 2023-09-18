package com.ichtus.hotelmanagementsystem.model.dto.booking;

import com.ichtus.hotelmanagementsystem.model.anotations.ValidRoomCapacity;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Polymorphism;

import java.util.Date;

@Data
@Accessors(chain = true)
public class RequestNewBooking {

    @PositiveOrZero
    private long roomId;

    @PositiveOrZero
    private long hotelId;

    @ValidRoomCapacity
    private int numberOfGuests;

    @NotNull
    @Future
    private Date startDate;

    @NotNull
    @Future
    private Date endDate;

}
