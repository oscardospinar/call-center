package com.almundo.callcenter.exception;

public class NoEmployeesExist extends CallCenterException {
    public NoEmployeesExist(String message) {
        super(message);
    }

    public NoEmployeesExist(String message, Throwable cause) {
        super(message, cause);
    }
}
