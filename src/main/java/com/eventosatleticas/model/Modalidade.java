package com.eventosatleticas.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "modalidades", schema = "eventos_schema")
public class Modalidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome; // Exemplo: "Futebol", "Vôlei", etc.

    private String regras; // Opcional: descrição das regras
}