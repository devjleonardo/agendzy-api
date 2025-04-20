package com.agendzy.api.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HttpClientException extends RuntimeException {

    private final HttpStatus status;

    public HttpClientException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

}
