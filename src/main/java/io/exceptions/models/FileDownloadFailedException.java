package io.exceptions.models;

import org.springframework.http.HttpStatus;

public class FileDownloadFailedException extends Exception {
    private HttpStatus httpStatus;

    public FileDownloadFailedException (String message) {
        super(message);
        this.httpStatus = HttpStatus.NOT_FOUND;
    }

    public HttpStatus getHttpStatus () {
        return httpStatus;
    }
}
