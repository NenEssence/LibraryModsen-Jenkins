package com.testproject.identityservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testproject.identityservice.dto.AuthRequest;
import com.testproject.identityservice.model.UserCredential;
import com.testproject.identityservice.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthController authController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getToken_ShouldReturnToken_WhenCredentialsAreValid() throws Exception {

        AuthRequest authRequest = new AuthRequest("testuser", "password");
        String expectedToken = "testToken";

        when(authService.generateToken(authRequest.getUsername())).thenReturn(expectedToken);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);


        mockMvc.perform(post("/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedToken));
    }

    @Test
    void validateToken_ShouldReturnValidMessage_WhenTokenIsValid() throws Exception {
        String token = "validToken";
        String validationMessage = "Token is valid";

        when(authService.validateToken(token)).thenReturn(validationMessage);

        mockMvc.perform(get("/auth/validate")
                        .param("token", token))
                .andExpect(status().isOk())
                .andExpect(content().string(validationMessage));
    }

    @Test
    void addNewUser_ShouldReturnSuccessMessage_WhenUserIsRegistered() throws Exception {
        UserCredential user = new UserCredential();
        user.setUsername("newuser");
        user.setEmail("newuser@email.com");
        user.setPassword("password123");

        String successMessage = "User added to system";
        when(authService.saveUser(any(UserCredential.class))).thenReturn(successMessage);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().string(successMessage));
    }
}
