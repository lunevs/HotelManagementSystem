package com.ichtus.hotelmanagementsystem.exceptions;


import java.io.Serial;

@SuppressWarnings("serial")
public class AccountAlreadyExists extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public AccountAlreadyExists(String name) {
        super("AccountAlreadyExists: User with name " + name + " already exists!");
    }

}
