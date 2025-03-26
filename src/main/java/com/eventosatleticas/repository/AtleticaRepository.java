package com.eventosatleticas.repository;

import com.eventosatleticas.model.Atletica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtleticaRepository extends JpaRepository<Atletica, Long> {
}