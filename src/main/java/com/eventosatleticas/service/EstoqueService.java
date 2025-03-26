package com.eventosatleticas.service;

import com.eventosatleticas.model.Estoque;
import com.eventosatleticas.repository.EstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstoqueService {

    @Autowired
    private EstoqueRepository estoqueRepository;

    public List<Estoque> listarTodos() {
        return estoqueRepository.findAll();
    }

    public Estoque adicionarItem(Estoque item) {
        return estoqueRepository.save(item);
    }
}