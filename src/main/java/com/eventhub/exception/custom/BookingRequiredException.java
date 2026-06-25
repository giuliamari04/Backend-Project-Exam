package com.eventhub.exception.custom;

public class BookingRequiredException extends BaseException {

    public BookingRequiredException() {
        super("BOOKING_REQUIRED", "You must book this event before reviewing it");
    }
}