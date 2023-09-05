package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.exceptions.AccountNotFoundException;
import com.ichtus.hotelmanagementsystem.model.dictionaries.BookingStatus;
import com.ichtus.hotelmanagementsystem.model.dto.booking.CreateBookingRequestDto;
import com.ichtus.hotelmanagementsystem.model.dto.booking.GetBookingsResponseDto;
import com.ichtus.hotelmanagementsystem.model.entities.Account;
import com.ichtus.hotelmanagementsystem.model.entities.Booking;
import com.ichtus.hotelmanagementsystem.model.entities.Location;
import com.ichtus.hotelmanagementsystem.model.entities.Room;
import com.ichtus.hotelmanagementsystem.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomService roomService;
    private final AccountService accountService;

    public List<GetBookingsResponseDto> getMyBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(GetBookingsResponseDto::of)
                .toList();
    }

    public GetBookingsResponseDto bookRoom(CreateBookingRequestDto requestDto, String accountName) {
        log.info(requestDto.getRoomId() + " " + requestDto.getStartDate());
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
