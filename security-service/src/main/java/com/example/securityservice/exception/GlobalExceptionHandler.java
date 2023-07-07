package com.example.securityservice.exception;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> userNotFound(UserNotFoundException e){
        ErrorResponse response = new ErrorResponse("User not found", LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(404));
    }
}
