package com.ichtus.hotelmanagementsystem.exceptions;

import java.io.Serial;

@SuppressWarnings("serial")
public class FreeDatesForRoomNotFountException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public FreeDatesForRoomNotFountException() {
        super("FreeDatesForRoomNotFountException: Room already booked for this period");
    }
}
