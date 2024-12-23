package com.testproject.identityservice.service;

import com.testproject.identityservice.model.UserCredential;
import com.testproject.identityservice.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserCredentialRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    public String saveUser(UserCredential credential) {
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        repository.save(credential);
        return "User added to system";
    }

    public String generateToken(String username){
        return jwtService.generateToken(username);
    }

    public String validateToken(String token) {
        jwtService.validateToken(token);
        return "Token is valid";
    }
}
