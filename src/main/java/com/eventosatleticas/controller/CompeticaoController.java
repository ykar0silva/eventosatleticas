package com.eventosatleticas.controller;

import com.eventosatleticas.model.Competicao;
import com.eventosatleticas.service.CompeticaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/competicoes")
public class CompeticaoController {
    @Autowired
    private CompeticaoService competicaoService;

    @GetMapping
    public List<Competicao> listarCompeticoes() {
        return competicaoService.listarTodas();
    }

    @GetMapping("/modalidade/{modalidadeId}")
    public List<Competicao> listarPorModalidade(@PathVariable Long modalidadeId) {
        return competicaoService.listarPorModalidade(modalidadeId);
    }

    @PostMapping
    public Competicao criarCompeticao(@RequestBody Competicao competicao) {
        return competicaoService.criarCompeticao(competicao);
    }
}