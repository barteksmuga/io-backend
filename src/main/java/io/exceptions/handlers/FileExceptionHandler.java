package io.exceptions.handlers;

import io.exceptions.models.FileUploadFailedException;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

@ControllerAdvice
public class FileExceptionHandler {

    @ExceptionHandler(FileUploadFailedException.class)
    public ResponseEntity handleFileUploadFailedException (FileUploadFailedException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity handleMaxFileSizeExceeded (MultipartException e) {
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleFileNotFoundException (NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
