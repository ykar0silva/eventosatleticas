package com.eventosatleticas.repository;

import com.eventosatleticas.model.Competicao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompeticaoRepository extends JpaRepository<Competicao, Long> {
    List<Competicao> findByModalidadeId(Long modalidadeId);
}