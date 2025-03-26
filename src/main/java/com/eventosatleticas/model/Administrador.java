package com.eventosatleticas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "administradores", schema = "eventos_schema")
public class Administrador extends Usuario {
    private String nivelAcesso; 
    
    
    // Getters e Setters
    public String getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(String nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }
}