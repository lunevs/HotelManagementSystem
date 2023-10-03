package com.ichtus.hotelmanagementsystem.exceptions;

/**
 * Custom exception class for handle booking exception: no free dates for room booking
 * @author smlunev
 */
public class FreeDatesForRoomNotFountException extends RuntimeException {

    public FreeDatesForRoomNotFountException() {
        super("FreeDatesForRoomNotFountException: Room already booked for this period");
    }
}
