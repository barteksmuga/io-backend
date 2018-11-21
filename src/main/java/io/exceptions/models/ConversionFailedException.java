package io.exceptions.models;

import org.springframework.http.HttpStatus;

public class ConversionFailedException extends Exception {
    private HttpStatus httpStatus;

    public ConversionFailedException (String message) {
        super(message);
        this.httpStatus = HttpStatus.EXPECTATION_FAILED;
    }

    public HttpStatus getHttpStatus () {
        return httpStatus;
    }
}
