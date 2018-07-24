package com.almundo.callcenter.exception;

public class EmployeeUnavailableException extends CallCenterException {
    public EmployeeUnavailableException(String message) {
        super(message);
    }

    public EmployeeUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
