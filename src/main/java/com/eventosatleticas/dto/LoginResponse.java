package com.eventosatleticas.dto;

public class LoginResponse {
    private String token;

    // Construtor
    public LoginResponse(String token) {
        this.token = token;
    }

    // Getter
    public String getToken() {
        return token;
    }
}