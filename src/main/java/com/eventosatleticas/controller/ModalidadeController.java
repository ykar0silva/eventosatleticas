package com.eventosatleticas.controller;

import com.eventosatleticas.model.Modalidade;
import com.eventosatleticas.service.ModalidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modalidades")
public class ModalidadeController {
    @Autowired
    private ModalidadeService modalidadeService;

    @GetMapping
    public List<Modalidade> listarModalidades() {
        return modalidadeService.listarTodas();
    }

    @PostMapping
    public Modalidade criarModalidade(@RequestBody Modalidade modalidade) {
        return modalidadeService.criarModalidade(modalidade);
    }
}