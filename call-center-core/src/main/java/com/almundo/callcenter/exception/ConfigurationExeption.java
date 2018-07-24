package com.almundo.callcenter.exception;

public class ConfigurationExeption extends CallCenterException {
    public ConfigurationExeption(String message) {
        super(message);
    }

    public ConfigurationExeption(String message, Throwable cause) {
        super(message, cause);
    }
}
