package com.agendzy.api.core.exception;

public class JDAConfigurationException extends RuntimeException {

    public JDAConfigurationException(String message) {
        super(message);
    }

    public JDAConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

}
