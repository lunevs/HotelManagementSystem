package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.exceptions.FreeDatesForRoomNotFountException;
import com.ichtus.hotelmanagementsystem.model.dictionaries.BookingStatus;
import com.ichtus.hotelmanagementsystem.model.dto.booking.RequestNewBooking;
import com.ichtus.hotelmanagementsystem.model.dto.booking.ResponseBooking;
import com.ichtus.hotelmanagementsystem.model.entities.Account;
import com.ichtus.hotelmanagementsystem.model.entities.Booking;
import com.ichtus.hotelmanagementsystem.model.entities.Room;
import com.ichtus.hotelmanagementsystem.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomService roomService;
    private final AccountService accountService;


    public List<ResponseBooking> getMyBookings() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String accountName = auth.getName();
        Stream<Booking> bookingStream = bookingRepository.findAll().stream();
        Stream<? extends GrantedAuthority> authStream = auth.getAuthorities().stream();

        if (authStream.anyMatch(el -> !el.getAuthority().contains("ADMIN"))) {
            bookingStream = bookingStream
                    .filter(
                            el -> el.getAccount().getAccountName().equals(accountName)
                    );
        }
        return bookingStream.map(ResponseBooking::of).toList();
    }

    public ResponseBooking doBooking(RequestNewBooking requestDto, String accountName) {
        if (!roomIsAvailable(requestDto)) {
            throw new FreeDatesForRoomNotFountException();
        }
        Room room = roomService.findById(requestDto.getRoomId());
        Account account = accountService.findAccountByName(accountName);
        Booking booking = new Booking()
                .setBookingStatus(BookingStatus.ACTIVE)
                .setRoom(room)
                .setStartDate(requestDto.getStartDate())
                .setEndDate(requestDto.getEndDate())
                .setNumberOfGuests(requestDto.getNumberOfGuests())
                .setAccount(account)
                .setDeleted(false);
        return ResponseBooking.of(bookingRepository.save(booking));
    }

    private boolean roomIsAvailable(RequestNewBooking booking) {

        int existingBookings = bookingRepository.checkBookingsForRoom(
                booking.getRoomId(),
                booking.getStartDate(),
                booking.getEndDate()
        ).size();

        return !(existingBookings > 0);
    }

}
