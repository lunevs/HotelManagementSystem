package com.ichtus.hotelmanagementsystem.exceptions;

import java.io.Serial;

@SuppressWarnings("serial")
public class BadAuthException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public BadAuthException() {
        super("Authentication Exception: Incorrect login or password! Try again.");
    }

}
