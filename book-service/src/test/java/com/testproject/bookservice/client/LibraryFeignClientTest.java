package com.testproject.bookservice.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class LibraryFeignClientTest {

    @MockBean
    private LibraryFeignClient mockLibraryFeignClient;

    @Test
    void testCreateBookClaim() {
        Long bookId = 1L;

        when(mockLibraryFeignClient.createBookClaim(bookId)).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));

        ResponseEntity<Void> response = mockLibraryFeignClient.createBookClaim(bookId);

        verify(mockLibraryFeignClient, times(1)).createBookClaim(bookId);
        assert response.getStatusCode() == HttpStatus.CREATED;
    }
}
