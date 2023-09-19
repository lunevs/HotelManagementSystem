package com.ichtus.hotelmanagementsystem.exceptions;

import java.io.Serial;

@SuppressWarnings("serial")
public class DefaultBadRequestException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public DefaultBadRequestException(String message) {
        super(message);
    }
}
