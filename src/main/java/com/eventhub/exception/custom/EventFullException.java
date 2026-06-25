package com.eventhub.exception.custom;

public class EventFullException extends BaseException {

    public EventFullException() {
        super("EVENT_FULL", "Event is already full");
    }
}