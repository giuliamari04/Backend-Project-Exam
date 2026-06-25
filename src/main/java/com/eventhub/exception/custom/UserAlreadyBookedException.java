package com.eventhub.exception.custom;

public class UserAlreadyBookedException extends BaseException {

    public UserAlreadyBookedException() {
        super("USER_ALREADY_BOOKED", "User already booked this event");
    }
}