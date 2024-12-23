package com.testproject.bookservice.exception.exceptionHandling;

import com.testproject.bookservice.exception.BookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleBookNotFoundException(BookNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDetails("Resource not found", Map.of("Error",ex.getMessage())));
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorDetails> handleSqlException(SQLException ex) {
        return ResponseEntity.badRequest().body(new ErrorDetails("Validation error", Map.of("SqlError", ex.getMessage())));
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
}

