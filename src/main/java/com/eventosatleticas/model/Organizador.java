package com.eventosatleticas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "organizadores", schema = "eventos_schema")
public class Organizador extends Usuario {
    // Atributos específicos do organizador, se necessário
    private String funcao; // Exemplo: "Logística", "Competição", etc.

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }
}