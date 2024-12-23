package com.testproject.libraryservice.controller;

import com.testproject.libraryservice.dto.BookClaimRequest;
import com.testproject.libraryservice.dto.BookClaimResponse;
import com.testproject.libraryservice.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/library")
public class LibraryController {

    @Autowired
    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping
    public ResponseEntity<Page<BookClaimResponse>> getAll(@RequestParam(value = "offset", required = false) Integer offset,
                                                          @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (null == offset) offset = 0;
        if (null == pageSize) pageSize = 10;
        return ResponseEntity.ok(libraryService.getBookClaimsPage(PageRequest.of(offset, pageSize)));
    }

    @GetMapping(value = "/available")
    public ResponseEntity<Page<BookClaimResponse>> getAvailable(@RequestParam(value = "offset", required = false) Integer offset,
                                                                @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (null == offset) offset = 0;
        if (null == pageSize) pageSize = 10;
        return ResponseEntity.ok(libraryService.getAvailableBookClaims(PageRequest.of(offset, pageSize)));
    }

    @GetMapping(value = "/book-id/{bookId}")
    public ResponseEntity<BookClaimResponse> getBookClaimById(@PathVariable("bookId") Long bookId) {
        return ResponseEntity.ok(libraryService.getBookClaimByBookId(bookId));
    }

    @PostMapping(value = "/book-id/{bookId}")
    public ResponseEntity<BookClaimResponse> createBookClaim(@PathVariable("bookId") Long bookId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryService.createBookClaim(bookId));
    }

    @PutMapping(value = "/book-id/{bookId}")
    public ResponseEntity<BookClaimResponse> updateBookClaim(@PathVariable("bookId") Long bookId, @RequestBody BookClaimRequest bookClaimRequest) {
        return ResponseEntity.ok(libraryService.updateBookClaim(bookId, bookClaimRequest));
    }

    @DeleteMapping(value = "/book-id/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookClaim(@PathVariable("bookId") Long bookId) {
        libraryService.deleteBookClaim(bookId);
    }
}
