package com.almundo.callcenter.exception;

public class InvalidPriorityException extends CallCenterException {
    public InvalidPriorityException(String message) {
        super(message);
    }

    public InvalidPriorityException(String message, Throwable cause) {
        super(message, cause);
    }
}
