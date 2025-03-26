package com.eventosatleticas.service;

import com.eventosatleticas.model.Atletica;
import com.eventosatleticas.repository.AtleticaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtleticaService {

    @Autowired
    private AtleticaRepository atleticaRepository;

    public List<Atletica> listarTodas() {
        return atleticaRepository.findAll();
    }

    public Atletica criarAtlética(Atletica atletica) {
        return atleticaRepository.save(atletica);
    }
}