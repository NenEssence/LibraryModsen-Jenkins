package com.testproject.libraryservice.exception.exceptionHandling;


import com.testproject.libraryservice.exception.BookClaimNotFoundException;
import com.testproject.libraryservice.exception.IncorrectDateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorDetails> handleBookNotFoundException(SQLException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDetails("Bad request", Map.of("SQLException:", ex.getMessage())));
    }

    @ExceptionHandler(IncorrectDateException.class)
    public ResponseEntity<ErrorDetails> handleBookNotFoundException(IncorrectDateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDetails("Bad request", Map.of("Dates:", ex.getMessage())));
    }

    @ExceptionHandler(BookClaimNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleBookNotFoundException(BookClaimNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDetails("Resource not found", Map.of("Error",ex.getMessage())));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleValidationException(MethodArgumentNotValidException ex) {
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        Map<String, String> errorMap = errors.stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (a, b) -> a, LinkedHashMap::new
                ));
        return ResponseEntity.badRequest().body(new ErrorDetails("Validation error", errorMap));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDetails> handleDefaultHandlerException(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDetails("Bad request", Map.of("Dates:", ex.getMessage())));
    }
}
