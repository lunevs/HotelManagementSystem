package com.ichtus.hotelmanagementsystem.exceptions;

import java.io.Serial;

@SuppressWarnings("serial")
public class IncorrectDateForBookingException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public IncorrectDateForBookingException() {
        super("IncorrectDateForBookingException: Booking impossible for this period");
    }
}
