package com.testproject.identityservice.service;

import com.testproject.identityservice.model.UserCredential;
import com.testproject.identityservice.repository.UserCredentialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserCredentialRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveUser_ShouldReturnSuccessMessage() {
        UserCredential credential = new UserCredential();
        credential.setUsername("testuser");
        credential.setPassword("password");

        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");
        when(repository.save(any(UserCredential.class))).thenReturn(credential);

        String result = authService.saveUser(credential);

        assertEquals("User added to system", result);
        verify(passwordEncoder).encode("password");
        verify(repository).save(any(UserCredential.class));
    }

    @Test
    void generateToken_ShouldReturnToken() {
        String username = "testuser";
        String expectedToken = "sampleToken";
        when(jwtService.generateToken(username)).thenReturn(expectedToken);

        String token = authService.generateToken(username);

        assertEquals(expectedToken, token);
        verify(jwtService).generateToken(username);
    }

    @Test
    void validateToken_ShouldCallJwtService() {

        String token = "sampleToken";

        authService.validateToken(token);

        verify(jwtService).validateToken(token);
    }
}
