package com.testproject.bookservice.controller;

import com.testproject.bookservice.dto.BookRequest;
import com.testproject.bookservice.dto.BookResponse;
import com.testproject.bookservice.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping()
    public ResponseEntity<Page<BookResponse>> getBooks(@RequestParam(value = "offset", required = false) Integer offset,
                                                       @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (null == offset) offset = 0;
        if (null == pageSize) pageSize = 10;
        return ResponseEntity.ok(bookService.getBooksPage(PageRequest.of(offset, pageSize)));
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookResponse> getBookByIsbn(@PathVariable("isbn") String isbn) {
        return ResponseEntity.ok(bookService.getBookByIsbn(isbn));
    }

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookRequest bookRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(bookRequest));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable("id") Long id, @Valid @RequestBody BookRequest bookRequest) {
        return ResponseEntity.ok(bookService.updateBook(id, bookRequest));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable("id") Long id) {
        bookService.deleteById(id);
    }
}
