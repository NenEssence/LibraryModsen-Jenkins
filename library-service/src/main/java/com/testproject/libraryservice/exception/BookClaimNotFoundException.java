package com.testproject.libraryservice.exception;

public class BookClaimNotFoundException extends RuntimeException {
    public BookClaimNotFoundException(String message) {
        super(message);
    }
}
