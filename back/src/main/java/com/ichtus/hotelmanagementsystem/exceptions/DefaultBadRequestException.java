package com.ichtus.hotelmanagementsystem.exceptions;

/**
 * Custom exception class for handle bad requests exceptions
 * @author smlunev
 */
public class DefaultBadRequestException extends RuntimeException {

    public DefaultBadRequestException(String message) {
        super(message);
    }
}
