package com.ichtus.hotelmanagementsystem.model.dto.booking;

import com.ichtus.hotelmanagementsystem.model.entities.Booking;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GetBookingStatusResponseDto {

    private long bookingId;
    private String status;
    private String statusDescription;

    public static GetBookingStatusResponseDto of(Booking booking) {
        return new GetBookingStatusResponseDto()
                .setBookingId(booking.getId())
                .setStatus(booking.getBookingStatus().name())
                .setStatusDescription("");
    }
}
