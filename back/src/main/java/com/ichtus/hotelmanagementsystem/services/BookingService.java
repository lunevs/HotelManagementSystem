package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.exceptions.*;
import com.ichtus.hotelmanagementsystem.model.dictionaries.BookingStatus;
import com.ichtus.hotelmanagementsystem.model.dto.booking.*;
import com.ichtus.hotelmanagementsystem.model.entities.*;
import com.ichtus.hotelmanagementsystem.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Defines services to interact with rooms bookings
 * @author smlunev
 */
@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomService roomService;
    private final AccountService accountService;

    /**
     * Get current user bookings. Admin gets bookings of all users
     * @return list ResponseBooking with bookings data
     */
    public List<ResponseBooking> getMyBookings() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String accountName = auth.getName();
        Stream<Booking> bookingStream = bookingRepository.findAllByDeleted(false).stream();
        Stream<? extends GrantedAuthority> authStream = auth.getAuthorities().stream();

        if (authStream.anyMatch(el -> !el.getAuthority().contains("ADMIN"))) {
            bookingStream = bookingStream
                    .filter(
                            el -> el.getAccount().getAccountName().equals(accountName)
                    );
        }
        return bookingStream
                .filter(booking -> !booking.getRoom().isDeleted())
                .map(ResponseBooking::of).toList();
    }

    /**
     * Book a room for user with accountName
     * @param requestDto dto with booking parameters
     * @param accountName user who book a room
     * @return dto ResponseBooking with created booking information
     */
    public ResponseBooking doBooking(RequestNewBooking requestDto, String accountName) {
        if (!roomIsAvailable(requestDto)) {
            throw new FreeDatesForRoomNotFountException();
        }
        if (requestDto.getStartDate().after(requestDto.getEndDate())) {
            throw new IncorrectDateForBookingException();
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

    /**
     * Internal method for checking if room is available for booking
      * @param booking dto with booking parameters
     * @return true if available or false if not
     */
    private boolean roomIsAvailable(RequestNewBooking booking) {
        List<Booking> existingBookings = bookingRepository.checkBookingsForRoom(
                booking.getRoomId(),
                booking.getStartDate(),
                booking.getEndDate());
        return !(existingBookings.size() > 0);
    }

    /**
     * Allows cancel booking
     * @param bookingId canceled booking id
     */
    public void cancelBooking(Long bookingId) {
        Optional<Booking> bookingToDelete = bookingRepository.findById(bookingId);
        if (bookingToDelete.isPresent()) {
            bookingRepository.save(
                    bookingToDelete.get().setDeleted(true)
            );
        } else {
            throw new ObjectNotFoundException(bookingId, Booking.class.getName());
        }
    }

}
