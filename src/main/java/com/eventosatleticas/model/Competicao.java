package com.eventosatleticas.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "competicao", schema = "eventos_schema")
public class Competicao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "modalidade_id", nullable = false)
    private Modalidade modalidade;

    private String descricao;

    @ManyToOne
    @JoinColumn(name = "atletica1_id")
    private Atletica atletica1;

    @ManyToOne
    @JoinColumn(name = "atletica2_id")
    private Atletica atletica2;

    private String resultado;

    private String status; // Exemplo: "Em andamento", "Finalizada"
}