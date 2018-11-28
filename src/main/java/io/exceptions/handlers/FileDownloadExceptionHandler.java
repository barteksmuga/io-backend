package io.exceptions.handlers;

import io.exceptions.models.FileDownloadFailedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FileDownloadExceptionHandler {

    @ExceptionHandler(FileDownloadFailedException.class)
    public ResponseEntity handleFileDownloadFailedException (FileDownloadFailedException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
}
