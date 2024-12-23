package com.testproject.bookservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("library-service")
public interface LibraryFeignClient {
    @PostMapping(
            value = "/library/book-id/{bookId}")
    ResponseEntity<Void> createBookClaim(@PathVariable("bookId") Long bookId);
}
