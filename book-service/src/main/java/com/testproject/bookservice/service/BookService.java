package com.testproject.bookservice.service;

import com.testproject.bookservice.client.LibraryFeignClient;
import com.testproject.bookservice.dto.BookRequest;
import com.testproject.bookservice.dto.BookResponse;
import com.testproject.bookservice.exception.BookNotFoundException;
import com.testproject.bookservice.model.Book;
import com.testproject.bookservice.repository.BookRepository;
import com.testproject.bookservice.util.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LibraryFeignClient libraryFeignClient;

    @Autowired
    private BookMapper bookMapper;

    public Page<BookResponse> getBooksPage(PageRequest pageRequest) {
        Page<Book> books = bookRepository.findAll(pageRequest);
        return books.map(book -> bookMapper.toBookResponse(book));
    }

    public BookResponse getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return bookMapper.toBookResponse(book.get());
        } else {
            throw new BookNotFoundException("Book with id " + id + " not found");
        }
    }

    public BookResponse getBookByIsbn(String isbn) {
        Optional<Book> book = bookRepository.findByIsbn(isbn);
        if (book.isPresent()) {
            return bookMapper.toBookResponse(book.get());
        } else {
            throw new BookNotFoundException("Book with isbn " + isbn + " not found");
        }
    }

    public BookResponse createBook(BookRequest bookRequest) {
        Book book = bookMapper.toBook(bookRequest);
        bookRepository.save(book);
        libraryFeignClient.createBookClaim(book.getId());
        return bookMapper.toBookResponse(book);
    }

    public BookResponse updateBook(Long id, BookRequest bookRequest) {
        Book updatedBook = bookMapper.toBook(bookRequest);
        if (bookRepository.findById(id).isPresent()) {
            updatedBook.setId(id);
            return bookMapper.toBookResponse(bookRepository.save(updatedBook));
        }
        throw new BookNotFoundException("Book with id " + id + " not found");
    }

    public void deleteById(Long id) {
        if (bookRepository.findById(id).isPresent()) {
            bookRepository.deleteById(id);
        } else {
            throw new BookNotFoundException("Book with id " + id + " not found");
        }
    }
}
