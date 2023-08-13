package com.ichtus.hotelmanagementsystem.exceptions;

import java.io.Serial;

@SuppressWarnings("serial")
public class LocationNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public LocationNotFoundException(Long id) {
        super("ResourceNotFoundException: Location with id " + id + " not found!");
    }

}
