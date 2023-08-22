package com.ichtus.hotelmanagementsystem.model.dto.booking;

import com.ichtus.hotelmanagementsystem.model.entities.Booking;
import com.ichtus.hotelmanagementsystem.model.dictionaries.BookingStatus;
import com.ichtus.hotelmanagementsystem.model.dto.room.GetRoomShortIntroResponseDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class GetBookingsResponseDto {

    private long id;

    private GetRoomShortIntroResponseDto bookedRoom;
    private Long accountId;
    private BookingStatus bookingStatus;
    private LocalDateTime startDate;
    private long numberOfNights;
    private int numberOfGuests;

    public static GetBookingsResponseDto of(Booking booking) {
        return new GetBookingsResponseDto()
                .setId(booking.getId())
                .setBookedRoom(GetRoomShortIntroResponseDto.of(booking.getRoom()))
                .setAccountId(booking.getAccount().getId())
                .setBookingStatus(booking.getBookingStatus())
                .setStartDate(booking.getStartDate())
                .setNumberOfGuests(booking.getNumberOfGuests())
                .setNumberOfNights(
                        ChronoUnit.DAYS.between(booking.getStartDate(),booking.getEndDate())
                );
    }
}
