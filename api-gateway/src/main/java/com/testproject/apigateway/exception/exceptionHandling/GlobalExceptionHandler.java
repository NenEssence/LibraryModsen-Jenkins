package com.testproject.apigateway.exception.exceptionHandling;


import com.testproject.apigateway.exception.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorDetails> handleBookNotFoundException(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDetails("Forbidden", Map.of("Error:", ex.getMessage())));
    }
}
