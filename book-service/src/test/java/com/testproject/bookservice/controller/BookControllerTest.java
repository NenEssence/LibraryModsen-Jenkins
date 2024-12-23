package com.testproject.bookservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testproject.bookservice.dto.BookRequest;
import com.testproject.bookservice.dto.BookResponse;
import com.testproject.bookservice.service.BookService;
import org.junit.jupiter.api.BeforeEach;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    private BookResponse bookResponse;
    private BookRequest bookRequest;

    @BeforeEach
    void setUp() {
        bookResponse = new BookResponse(1L, "123456789", "Test Title", "Test Genre", "Test Description", "Test Author");
        bookRequest = new BookRequest("9781234567890", "Test Title", "Test Genre", "Test Description", "Test Author");
    }

    @Test
    void getBooks_ShouldReturnPagedBooks() throws Exception {
        Page<BookResponse> page = new PageImpl<>(Collections.singletonList(bookResponse));
        when(bookService.getBooksPage(any(PageRequest.class))).thenReturn(page);

        mockMvc.perform(get("/books")
                        .param("offset", "0")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(bookResponse.getId()))
                .andExpect(jsonPath("$.content[0].isbn").value(bookResponse.getIsbn()));
    }

    @Test
    void getBookById_ShouldReturnBook() throws Exception {
        when(bookService.getBookById(anyLong())).thenReturn(bookResponse);

        mockMvc.perform(get("/books/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bookResponse.getId()))
                .andExpect(jsonPath("$.isbn").value(bookResponse.getIsbn()));
    }

    @Test
    void getBookByIsbn_ShouldReturnBook() throws Exception {
        when(bookService.getBookByIsbn(anyString())).thenReturn(bookResponse);

        mockMvc.perform(get("/books/isbn/{isbn}", "123456789"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value(bookResponse.getIsbn()));
    }

    @Test
    void createBook_ShouldReturnCreatedBook() throws Exception {
        when(bookService.createBook(any(BookRequest.class))).thenReturn(bookResponse);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.isbn").value(bookResponse.getIsbn()));
    }

    @Test
    void updateBook_ShouldReturnUpdatedBook() throws Exception {
        when(bookService.updateBook(anyLong(), any(BookRequest.class))).thenReturn(bookResponse);

        mockMvc.perform(put("/books/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value(bookResponse.getIsbn()));
    }

    @Test
    void deleteBook_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/books/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
