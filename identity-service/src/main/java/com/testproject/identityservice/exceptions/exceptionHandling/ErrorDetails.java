package com.testproject.identityservice.exceptions.exceptionHandling;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class ErrorDetails {
    private String message;
    private Map<String, String> errors;
}
