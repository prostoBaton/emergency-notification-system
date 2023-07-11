package com.example.requestmanager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public class RecipientDto {

    @Pattern(regexp="(^$|[0-9]{11})")
    private String phone;

    @Email(message = "Email should be valid")
    private String email;

    public RecipientDto(String phone, String email) {
        this.phone = phone;
        this.email = email;
    }

    public RecipientDto() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
