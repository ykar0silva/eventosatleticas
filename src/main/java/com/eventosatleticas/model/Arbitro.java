package com.eventosatleticas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "arbitros", schema = "eventos_schema")
public class Arbitro extends Usuario {
    // Atributos específicos do árbitro, se necessário
    private String especialidade; // Exemplo: "Futebol", "Vôlei", etc.

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}