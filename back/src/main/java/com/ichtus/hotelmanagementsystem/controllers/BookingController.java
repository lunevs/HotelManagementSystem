package com.ichtus.hotelmanagementsystem.controllers;

import com.ichtus.hotelmanagementsystem.model.anotations.IsUser;
import com.ichtus.hotelmanagementsystem.model.dto.booking.RequestNewBooking;
import com.ichtus.hotelmanagementsystem.model.dto.booking.ResponseBooking;
import com.ichtus.hotelmanagementsystem.services.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Controller allows to manage booking process
 * @author smlunev
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    /**
     * Endpoint to get all no deleted bookings
     * @return list with ResponseBooking data
     */
    @GetMapping
    @IsUser
    public ResponseEntity<List<ResponseBooking>> getAll() {
        return ResponseEntity.ok(bookingService.getMyBookings());
    }

    /**
     * Endpoint to book a room
     * @param requestDto dto with booking information
     * @param principal authenticated user information
     * @return dto ResponseBooking with created booking information
     */
    @PostMapping
    @IsUser
    public ResponseEntity<ResponseBooking> doBooking(@Valid @RequestBody RequestNewBooking requestDto, Principal principal) {
        return ResponseEntity.ok(bookingService.doBooking(requestDto, principal.getName()));
    }

    /**
     * Endpoint to cancel booking
     * @param bookingId booking id to cancel
     * @return empty response with status 200
     */
    @DeleteMapping("/{bookingId}")
    @IsUser
    public ResponseEntity<?> cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
