package com.eventhub.exception.custom;

public class EventNotFoundException extends BaseException {

    public EventNotFoundException() {
        super("EVENT_NOT_FOUND", "Event not found");
    }
}