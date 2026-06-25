package com.eventhub.exception.custom;

public class UserAlreadyReviewedException extends BaseException {

    public UserAlreadyReviewedException() {
        super("USER_ALREADY_REVIEWED", "User already reviewed this event");
    }
}