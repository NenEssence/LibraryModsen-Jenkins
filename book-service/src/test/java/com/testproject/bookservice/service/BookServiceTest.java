package com.testproject.bookservice.service;

import com.testproject.bookservice.client.LibraryFeignClient;
import com.testproject.bookservice.dto.BookRequest;
import com.testproject.bookservice.dto.BookResponse;
import com.testproject.bookservice.exception.BookNotFoundException;
import com.testproject.bookservice.model.Book;
import com.testproject.bookservice.repository.BookRepository;
import com.testproject.bookservice.util.mapper.BookMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private LibraryFeignClient libraryFeignClient;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private BookRequest bookRequest;
    private BookResponse bookResponse;

    @BeforeEach
    void setUp() {
        book = new Book(1L, "9874567890", "Test Book", "Genre", "Description","Author");
        bookRequest = new BookRequest("9874567890", "Test Book", "Genre", "Description","Author");
        bookResponse = new BookResponse(1L, "9874567890", "Test Book", "Genre", "Description","Author");
    }

    @Test
    void getBooksPage_shouldReturnPageOfBooks() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Book> books = new PageImpl<>(Arrays.asList(book));
        when(bookRepository.findAll(pageRequest)).thenReturn(books);
        when(bookMapper.toBookResponse(book)).thenReturn(bookResponse);

        Page<BookResponse> result = bookService.getBooksPage(pageRequest);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(bookResponse, result.getContent().get(0));
        verify(bookRepository).findAll(pageRequest);
    }

    @Test
    void getBookById_shouldReturnBook_whenBookExists() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookMapper.toBookResponse(book)).thenReturn(bookResponse);

        BookResponse result = bookService.getBookById(1L);

        assertNotNull(result);
        assertEquals(bookResponse, result);
        verify(bookRepository).findById(anyLong());
    }

    @Test
    void getBookById_shouldThrowBookNotFoundException_whenBookDoesNotExist() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.getBookById(1L));
        verify(bookRepository).findById(anyLong());
    }

    @Test
    void getBookByIsbn_shouldReturnBook_whenBookExists() {
        when(bookRepository.findByIsbn(anyString())).thenReturn(Optional.of(book));
        when(bookMapper.toBookResponse(book)).thenReturn(bookResponse);

        BookResponse result = bookService.getBookByIsbn("9874567890");

        assertNotNull(result);
        assertEquals(bookResponse, result);
        verify(bookRepository).findByIsbn(anyString());
    }

    @Test
    void getBookByIsbn_shouldThrowBookNotFoundException_whenBookDoesNotExist() {
        when(bookRepository.findByIsbn(anyString())).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.getBookByIsbn("9874567890"));
        verify(bookRepository).findByIsbn(anyString());
    }

    @Test
    void createBook_shouldCreateAndReturnBook() {
        when(bookMapper.toBook(any(BookRequest.class))).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.toBookResponse(book)).thenReturn(bookResponse);

        BookResponse result = bookService.createBook(bookRequest);

        assertNotNull(result);
        assertEquals(bookResponse, result);
        verify(bookRepository).save(any(Book.class));
        verify(libraryFeignClient).createBookClaim(book.getId());
    }

    @Test
    void updateBook_shouldUpdateAndReturnBook_whenBookExists() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookMapper.toBook(any(BookRequest.class))).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.toBookResponse(book)).thenReturn(bookResponse);

        BookResponse result = bookService.updateBook(1L, bookRequest);

        assertNotNull(result);
        assertEquals(bookResponse, result);
        verify(bookRepository).save(book);
    }

    @Test
    void updateBook_shouldThrowBookNotFoundException_whenBookDoesNotExist() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.updateBook(1L, bookRequest));
        verify(bookRepository).findById(anyLong());
    }

    @Test
    void deleteById_shouldDeleteBook_whenBookExists() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        bookService.deleteById(1L);

        verify(bookRepository).deleteById(anyLong());
    }

    @Test
    void deleteById_shouldThrowBookNotFoundException_whenBookDoesNotExist() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.deleteById(1L));
        verify(bookRepository).findById(anyLong());
    }
}
