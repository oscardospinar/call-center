package com.almundo.callcenter.exception;

/**
 * The basic exception for the CallCenter project.
 */
public class CallCenterException extends RuntimeException {
    public CallCenterException(String message) {
        super(message);
    }

    public CallCenterException(String message, Throwable cause) {
        super(message, cause);
    }
}
