package com.ichtus.hotelmanagementsystem.exceptions;

import java.io.Serial;

@SuppressWarnings("serial")
public class AccountNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public AccountNotFoundException(String name) {
        super("AccountNotFoundException: User with name " + name + " not found!");
    }

    public AccountNotFoundException(Long id) {
        super("AccountNotFoundException: User with id " + id + " not found!");
    }
}
