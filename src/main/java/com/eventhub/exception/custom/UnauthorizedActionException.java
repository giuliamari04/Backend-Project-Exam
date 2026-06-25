package com.eventhub.exception.custom;

public class UnauthorizedActionException extends BaseException {

    public UnauthorizedActionException() {
        super("UNAUTHORIZED_ACTION", "You are not allowed to perform this action");
    }
}