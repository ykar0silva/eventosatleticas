package com.eventosatleticas.dto;

public class LoginResponse {
    private String token;


    public LoginResponse(String token) {
        this.token = token;
    }

    // Getter para token
    public String getToken() {
        return token;
    }

    // Setter para token
    public void setToken(String token) {
        this.token = token;
    }
}