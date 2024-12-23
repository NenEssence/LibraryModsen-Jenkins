package com.testproject.apigateway.exception.exceptionHandling;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class ErrorDetails {
    private String message;
    private Map<String, String> errors;
}
