package com.example.login.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.login.dto.AuthResponse;
import com.example.login.dto.LoginRequest;
import com.example.login.security.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        UserDetails userDetails =
                customUserDetailsService
                        .loadUserByUsername(
                                request.getEmail());

        String accessToken =
                jwtUtil.generateToken(userDetails);

        return new AuthResponse(
                accessToken,
                null
        );
    }
}
