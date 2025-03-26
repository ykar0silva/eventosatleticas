package com.eventosatleticas.service;

import com.eventosatleticas.model.Competicao;
import com.eventosatleticas.repository.CompeticaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompeticaoService {
    @Autowired
    private CompeticaoRepository competicaoRepository;

    public List<Competicao> listarTodas() {
        return competicaoRepository.findAll();
    }

    public List<Competicao> listarPorModalidade(Long modalidadeId) {
        return competicaoRepository.findByModalidadeId(modalidadeId);
    }

    public Competicao criarCompeticao(Competicao competicao) {
        return competicaoRepository.save(competicao);
    }
}