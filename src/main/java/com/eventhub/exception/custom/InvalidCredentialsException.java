package com.eventhub.exception.custom;

public class InvalidCredentialsException extends BaseException {

    public InvalidCredentialsException() {
        super("INVALID_CREDENTIALS", "Invalid email or password");
    }
}