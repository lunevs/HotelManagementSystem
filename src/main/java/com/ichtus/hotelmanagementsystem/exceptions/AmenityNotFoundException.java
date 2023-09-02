package com.ichtus.hotelmanagementsystem.exceptions;

import java.io.Serial;

@SuppressWarnings("serial")
public class AmenityNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public AmenityNotFoundException(Long id) {
        super("ResourceNotFoundException: Amenity with id " + id + " not found!");
    }

}
