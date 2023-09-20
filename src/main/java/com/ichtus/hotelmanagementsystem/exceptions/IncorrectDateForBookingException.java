package com.ichtus.hotelmanagementsystem.exceptions;

/**
 * Custom exception class for handle booking exception: end date before start date
 * @author smlunev
 */
public class IncorrectDateForBookingException extends RuntimeException {

    public IncorrectDateForBookingException() {
        super("IncorrectDateForBookingException: Booking impossible for this period");
    }
}
