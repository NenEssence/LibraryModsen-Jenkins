package com.testproject.identityservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
    }

    @Test
    void generateToken_ShouldReturnValidToken() {
        String username = "testuser";

        String token = jwtService.generateToken(username);

        assertNotNull(token);
        assertDoesNotThrow(() -> jwtService.validateToken(token));

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtService.getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        assertEquals(username, claims.getSubject());
    }

    @Test
    void validateToken_WithValidToken_ShouldNotThrowException() {
        String username = "validuser";
        String token = jwtService.generateToken(username);

        assertDoesNotThrow(() -> jwtService.validateToken(token));
    }

    @Test
    void validateToken_WithInvalidToken_ShouldThrowException() {
        String invalidToken = "invalidToken";

        assertThrows(Exception.class, () -> jwtService.validateToken(invalidToken));
    }

    @Test
    void createToken_ShouldReturnTokenWithExpectedClaims() {
        String username = "testuser";
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "USER");

        String token = jwtService.createToken(claims, username);

        assertNotNull(token);

        Claims tokenClaims = Jwts.parserBuilder()
                .setSigningKey(jwtService.getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        assertEquals(username, tokenClaims.getSubject());
        assertEquals("USER", tokenClaims.get("role"));
    }

    @Test
    void getSignKey_ShouldReturnKey() {
        Key key = jwtService.getSignKey();

        assertNotNull(key);
    }
}
