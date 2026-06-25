package com.eventhub.exception.custom;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException() {
        super("USER_NOT_FOUND", "User not found");
    }
}