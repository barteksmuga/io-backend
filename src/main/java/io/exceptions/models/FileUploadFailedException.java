package io.exceptions.models;

import org.springframework.http.HttpStatus;

public class FileUploadFailedException extends Exception {
    private HttpStatus httpStatus;

    public FileUploadFailedException (String message) {
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public HttpStatus getHttpStatus () {
        return httpStatus;
    }
}
