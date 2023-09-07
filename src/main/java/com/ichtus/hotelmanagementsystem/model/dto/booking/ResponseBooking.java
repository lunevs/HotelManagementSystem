package com.ichtus.hotelmanagementsystem.model.dto.booking;

import com.ichtus.hotelmanagementsystem.model.dto.room.ResponseRoomData;
import com.ichtus.hotelmanagementsystem.model.entities.Booking;
import com.ichtus.hotelmanagementsystem.model.dictionaries.BookingStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ResponseBooking {

    private long id;
    private int numberOfGuests;
    private Date startDate;
    private Date endDate;
    private Long accountId;
    private String accountName;
    private BookingStatus bookingStatus;
    private ResponseRoomData bookedRoom;

    public static ResponseBooking of(Booking booking) {
        return new ResponseBooking()
                .setId(booking.getId())
                .setBookedRoom(ResponseRoomData.of(booking.getRoom()))
                .setAccountId(booking.getAccount().getId())
                .setAccountName(booking.getAccount().getAccountName())
                .setBookingStatus(booking.getBookingStatus())
                .setStartDate(booking.getStartDate())
                .setEndDate(booking.getEndDate())
                .setNumberOfGuests(booking.getNumberOfGuests());
    }
}
