package com.ichtus.hotelmanagementsystem.exceptions;

import java.io.Serial;

@SuppressWarnings("serial")
public class HotelNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public HotelNotFoundException(Long id) {
        super("ResourceNotFoundException: Location with id " + id + " not found!");
    }

}
