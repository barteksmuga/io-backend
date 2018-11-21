package io.exceptions.handlers;

import io.exceptions.models.ConversionFailedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ConvertFileExceptionHandler {

    @ExceptionHandler
    public ResponseEntity handleException (ConversionFailedException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
}
