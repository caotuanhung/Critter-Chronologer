package com.udacity.jdnd.course3.critter.user.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException() {
    }

    public CustomerNotFoundException(String message) {
        super(message);
    }
}
