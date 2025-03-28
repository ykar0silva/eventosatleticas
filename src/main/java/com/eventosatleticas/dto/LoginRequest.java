package com.eventosatleticas.dto;

public class LoginRequest {
    private String email;
    private String senha;

    // Getter para email
    public String getEmail() {
        return email;
    }

    // Setter para email
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter para password
    public String getSenha() {
        return senha;
    }

    // Setter para password
    public void setSenha(String senha) {
        this.senha = senha;
    }
}