package com.eventhub.exception.custom;

public class EmailAlreadyExistsException extends BaseException {

    public EmailAlreadyExistsException() {
        super("EMAIL_ALREADY_EXISTS", "Email already exists");
    }
}
