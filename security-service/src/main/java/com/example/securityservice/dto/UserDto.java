package com.example.securityservice.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;

public class UserDto {

    @NotEmpty(message = "Username shouldn't be empty")
    private String username;

    @NotEmpty(message = "Password shouldn't be empty")
    private String password;

    public UserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
