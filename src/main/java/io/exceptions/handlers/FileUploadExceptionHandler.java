package io.exceptions.handlers;

import io.exceptions.models.FileUploadFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

@ControllerAdvice
public class FileUploadExceptionHandler {

    @ExceptionHandler(FileUploadFailedException.class)
    public ResponseEntity handleFileUploadFailedException (FileUploadFailedException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity handleMaxFileSizeExceeded (MultipartException e) {
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(e.getMessage());
    }
}
