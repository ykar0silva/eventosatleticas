package com.eventosatleticas.repository;

import com.eventosatleticas.model.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
}