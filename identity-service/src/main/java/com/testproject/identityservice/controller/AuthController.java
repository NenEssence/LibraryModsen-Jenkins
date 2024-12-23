package com.testproject.identityservice.controller;

import com.testproject.identityservice.dto.AuthRequest;
import com.testproject.identityservice.model.UserCredential;
import com.testproject.identityservice.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/token")
    public ResponseEntity<String> getToken(@RequestBody AuthRequest authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        return ResponseEntity.ok(service.generateToken(authRequest.getUsername()));
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestParam("token") String token) {

        return ResponseEntity.ok(service.validateToken(token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> addNewUser(@Valid @RequestBody UserCredential user) {
        return ResponseEntity.ok(service.saveUser(user));
    }
}
