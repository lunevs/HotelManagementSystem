package com.ichtus.hotelmanagementsystem.controllers;

import com.ichtus.hotelmanagementsystem.model.anotations.IsUser;
import com.ichtus.hotelmanagementsystem.model.dto.booking.RequestNewBooking;
import com.ichtus.hotelmanagementsystem.services.BookingService;
import jakarta.validation.Valid;
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
    @IsUser
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(bookingService.getMyBookings());
    }

    @PostMapping
    @IsUser
    public ResponseEntity<?> doBooking(@Valid @RequestBody RequestNewBooking requestDto, Principal principal) {
        System.out.println(requestDto);
        return ResponseEntity.ok(bookingService.doBooking(requestDto, principal.getName()));
    }

}
