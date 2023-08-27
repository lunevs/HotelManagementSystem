package com.ichtus.hotelmanagementsystem.exceptions;

import java.io.Serial;

@SuppressWarnings("serial")
public class RoomNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public RoomNotFoundException(String name) {
        super("RoomNotFoundException: Room with name " + name + " not found!");
    }

}
