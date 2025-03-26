package com.eventosatleticas.service;

import com.eventosatleticas.model.Modalidade;
import com.eventosatleticas.repository.ModalidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModalidadeService {
    @Autowired
    private ModalidadeRepository modalidadeRepository;

    public List<Modalidade> listarTodas() {
        return modalidadeRepository.findAll();
    }

    public Modalidade criarModalidade(Modalidade modalidade) {
        return modalidadeRepository.save(modalidade);
    }
}