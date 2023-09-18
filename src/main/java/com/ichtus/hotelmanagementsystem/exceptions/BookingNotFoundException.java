package com.ichtus.hotelmanagementsystem.exceptions;

import java.io.Serial;

@SuppressWarnings("serial")
public class BookingNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public BookingNotFoundException(Long id) {
        super("ResourceNotFoundException: Booking with id " + id + " not found!");
    }

}