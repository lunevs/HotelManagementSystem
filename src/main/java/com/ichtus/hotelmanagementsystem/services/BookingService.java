package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.exceptions.AccountNotFoundException;
import com.ichtus.hotelmanagementsystem.model.dictionaries.BookingStatus;
import com.ichtus.hotelmanagementsystem.model.dto.booking.CreateBookingRequestDto;
import com.ichtus.hotelmanagementsystem.model.dto.booking.GetBookingsResponseDto;
import com.ichtus.hotelmanagementsystem.model.entities.Account;
import com.ichtus.hotelmanagementsystem.model.entities.Booking;
import com.ichtus.hotelmanagementsystem.model.entities.Room;
import com.ichtus.hotelmanagementsystem.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
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


    public List<GetBookingsResponseDto> getMyBookings() {
        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();
        Stream<Booking> bookingStream = bookingRepository.findAll().stream();
        if (auth.getAuthorities().stream()
                .anyMatch(el -> !el.getAuthority().contains("ADMIN"))) {
            bookingStream = bookingStream
                    .filter(el -> el.getAccount().getAccountName().equals(auth.getName()));
        }
        return bookingStream
                    .map(GetBookingsResponseDto::of)
                    .toList();
    }

    public GetBookingsResponseDto bookRoom(CreateBookingRequestDto requestDto, String accountName) {
        Room room = roomService.findById(requestDto.getRoomId());
        Account account = accountService.findByName(accountName)
                .orElseThrow(() -> new AccountNotFoundException(accountName));
        Booking booking = new Booking()
                .setBookingStatus(BookingStatus.ACTIVE)
                .setRoom(room)
                .setStartDate(requestDto.getStartDate())
                .setEndDate(requestDto.getEndDate())
                .setNumberOfGuests(requestDto.getNumberOfGuests())
                .setAccount(account)
                .setDeleted(false);
        return GetBookingsResponseDto.of(bookingRepository.save(booking));
    }
}
