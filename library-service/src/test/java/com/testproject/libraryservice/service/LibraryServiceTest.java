package com.testproject.libraryservice.service;

import com.testproject.libraryservice.dto.BookClaimRequest;
import com.testproject.libraryservice.dto.BookClaimResponse;
import com.testproject.libraryservice.exception.BookClaimNotFoundException;
import com.testproject.libraryservice.exception.IncorrectDateException;
import com.testproject.libraryservice.mapper.BookClaimMapper;
import com.testproject.libraryservice.model.BookClaim;
import com.testproject.libraryservice.repository.LibraryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class LibraryServiceTest {

    @Mock
    private LibraryRepository libraryRepository;

    @Mock
    private BookClaimMapper bookClaimMapper;

    @InjectMocks
    private LibraryService libraryService;

    private BookClaim bookClaim;
    private BookClaim bookClaimEmpty;
    private BookClaimResponse bookClaimResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookClaim = new BookClaim();
        bookClaim.setBookId(1L);
        bookClaim.setClaimDate(Timestamp.valueOf(LocalDateTime.now().minusDays(5)));
        bookClaim.setReturnDate(Timestamp.valueOf(LocalDateTime.now().plusDays(5)));


        bookClaimEmpty = new BookClaim();
        bookClaimEmpty.setBookId(1L);
        bookClaimEmpty.setClaimDate(null);
        bookClaimEmpty.setReturnDate(null);


        bookClaimResponse = new BookClaimResponse();
        bookClaimResponse.setBookId(1L);
    }

    @Test
    void getBookClaimsPage_ShouldReturnPageOfBookClaimResponses() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<BookClaim> bookClaimPage = new PageImpl<>(Collections.singletonList(bookClaim));
        when(libraryRepository.findAll(pageRequest)).thenReturn(bookClaimPage);
        when(bookClaimMapper.toBookClaimResponse(any(BookClaim.class))).thenReturn(bookClaimResponse);

        Page<BookClaimResponse> result = libraryService.getBookClaimsPage(pageRequest);

        assertEquals(1, result.getContent().size());
        assertEquals(bookClaimResponse, result.getContent().get(0));
    }

    @Test
    void getAvailableBookClaims_ShouldReturnAvailableBookClaimResponses() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<BookClaim> bookClaimPage = new PageImpl<>(Collections.singletonList(bookClaim));
        when(libraryRepository.findAvailableBookCliams(any(LocalDateTime.class), eq(pageRequest))).thenReturn(bookClaimPage);
        when(bookClaimMapper.toBookClaimResponse(any(BookClaim.class))).thenReturn(bookClaimResponse);

        Page<BookClaimResponse> result = libraryService.getAvailableBookClaims(pageRequest);

        assertEquals(1, result.getContent().size());
        assertEquals(bookClaimResponse, result.getContent().get(0));
    }

    @Test
    void getBookClaimByBookId_ShouldReturnBookClaimResponse() {
        when(libraryRepository.getBookClaimByBookId(anyLong())).thenReturn(Optional.of(bookClaim));
        when(bookClaimMapper.toBookClaimResponse(bookClaim)).thenReturn(bookClaimResponse);

        BookClaimResponse result = libraryService.getBookClaimByBookId(1L);

        assertEquals(bookClaimResponse, result);
    }

    @Test
    void getBookClaimByBookId_ShouldThrowExceptionIfNotFound() {
        when(libraryRepository.getBookClaimByBookId(anyLong())).thenReturn(Optional.empty());

        assertThrows(BookClaimNotFoundException.class, () -> libraryService.getBookClaimByBookId(1L));
    }

    @Test
    void createBookClaim_ShouldReturnCreatedBookClaimResponse() {
        when(libraryRepository.save(any(BookClaim.class))).thenReturn(bookClaim);
        when(bookClaimMapper.toBookClaimResponse(bookClaim)).thenReturn(bookClaimResponse);

        BookClaimResponse result = libraryService.createBookClaim(1L);

        assertEquals(bookClaimResponse, result);
    }

    @Test
    void updateBookClaim_ShouldReturnUpdatedBookClaimResponse() {
        BookClaimRequest bookClaimRequest = new BookClaimRequest();
        bookClaimRequest.setClaimDate(Timestamp.valueOf(LocalDateTime.now().minusDays(3)));
        bookClaimRequest.setReturnDate(Timestamp.valueOf(LocalDateTime.now().plusDays(3)));

        when(libraryRepository.getBookClaimByBookId(anyLong())).thenReturn(Optional.of(bookClaim));
        when(bookClaimMapper.toBookClaim(bookClaimRequest)).thenReturn(bookClaim);
        when(libraryRepository.save(bookClaim)).thenReturn(bookClaim);
        when(bookClaimMapper.toBookClaimResponse(bookClaim)).thenReturn(bookClaimResponse);

        BookClaimResponse result = libraryService.updateBookClaim(1L, bookClaimRequest);

        assertEquals(bookClaimResponse, result);
    }

    @Test
    void updateBookClaim_ShouldThrowIncorrectDateExceptionIfDatesAreNull() {
        BookClaimRequest bookClaimRequest = new BookClaimRequest();
        when(libraryRepository.getBookClaimByBookId(anyLong())).thenReturn(Optional.of(bookClaimEmpty));
        when(bookClaimMapper.toBookClaim(bookClaimRequest)).thenReturn(bookClaimEmpty);

        assertThrows(IncorrectDateException.class, () -> libraryService.updateBookClaim(1L, bookClaimRequest));
    }

    @Test
    void deleteBookClaim_ShouldDeleteBookClaimIfExists() {
        when(libraryRepository.getBookClaimByBookId(anyLong())).thenReturn(Optional.of(bookClaim));

        libraryService.deleteBookClaim(1L);

        verify(libraryRepository, times(1)).deleteBookClaimByBookId(1L);
    }

    @Test
    void deleteBookClaim_ShouldThrowBookClaimNotFoundExceptionIfNotExists() {
        when(libraryRepository.getBookClaimByBookId(anyLong())).thenReturn(Optional.empty());

        assertThrows(BookClaimNotFoundException.class, () -> libraryService.deleteBookClaim(1L));
    }
}
