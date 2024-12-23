package com.testproject.libraryservice.controller;

import com.testproject.libraryservice.dto.BookClaimRequest;
import com.testproject.libraryservice.dto.BookClaimResponse;
import com.testproject.libraryservice.service.LibraryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LibraryController.class)
public class LibraryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryService libraryService;

    @Test
    public void getAll_shouldReturnPageOfBookClaims() throws Exception {
        BookClaimResponse bookClaimResponse = new BookClaimResponse(1L, null, null);
        Page<BookClaimResponse> page = new PageImpl<>(Collections.singletonList(bookClaimResponse));

        when(libraryService.getBookClaimsPage(any(PageRequest.class))).thenReturn(page);

        mockMvc.perform(get("/library")
                        .param("offset", "0")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].bookId", is(1)));
    }

    @Test
    public void getAvailable_shouldReturnPageOfAvailableBookClaims() throws Exception {
        BookClaimResponse bookClaimResponse = new BookClaimResponse(2L, null, null);
        Page<BookClaimResponse> page = new PageImpl<>(Collections.singletonList(bookClaimResponse));

        when(libraryService.getAvailableBookClaims(any(PageRequest.class))).thenReturn(page);

        mockMvc.perform(get("/library/available")
                        .param("offset", "0")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].bookId", is(2)));
    }

    @Test
    public void getBookClaimById_shouldReturnBookClaim() throws Exception {
        BookClaimResponse bookClaimResponse = new BookClaimResponse(3L, null, null);

        when(libraryService.getBookClaimByBookId(3L)).thenReturn(bookClaimResponse);

        mockMvc.perform(get("/library/book-id/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookId", is(3)));
    }

    @Test
    public void createBookClaim_shouldReturnCreatedBookClaim() throws Exception {
        BookClaimResponse bookClaimResponse = new BookClaimResponse(4L, null, null);

        when(libraryService.createBookClaim(4L)).thenReturn(bookClaimResponse);

        mockMvc.perform(post("/library/book-id/4"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookId", is(4)));
    }

    @Test
    public void updateBookClaim_shouldReturnUpdatedBookClaim() throws Exception {
        BookClaimResponse bookClaimResponse = new BookClaimResponse(5L, null, null);

        when(libraryService.updateBookClaim(eq(5L), any(BookClaimRequest.class))).thenReturn(bookClaimResponse);

        mockMvc.perform(put("/library/book-id/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"claimDate\":\"2024-10-01T10:00:00\",\"returnDate\":\"2024-10-10T10:00:00\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookId", is(5)));
    }

    @Test
    public void deleteBookClaim_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/library/book-id/6"))
                .andExpect(status().isNoContent());
    }
}
