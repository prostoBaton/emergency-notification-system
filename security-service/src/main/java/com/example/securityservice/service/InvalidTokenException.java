package com.example.securityservice.service;

import io.jsonwebtoken.JwtException;

public class InvalidTokenException extends JwtException {
    public InvalidTokenException(String message) {
        super(message);
    }
}
