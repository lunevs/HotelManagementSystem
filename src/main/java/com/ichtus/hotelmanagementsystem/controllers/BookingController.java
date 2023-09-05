package com.ichtus.hotelmanagementsystem.controllers;

import com.ichtus.hotelmanagementsystem.model.dto.booking.CreateBookingRequestDto;
import com.ichtus.hotelmanagementsystem.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(bookingService.getMyBookings());
    }

    @PostMapping
    public ResponseEntity<?> createNewBook(@RequestBody CreateBookingRequestDto requestDto, Principal principal) {
        return ResponseEntity.ok(bookingService.bookRoom(requestDto, principal.getName()));
    }
}
