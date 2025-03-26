package com.eventosatleticas.controller;

import com.eventosatleticas.model.Estoque;
import com.eventosatleticas.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;

    @GetMapping
    public List<Estoque> listarEstoque() {
        return estoqueService.listarTodos();
    }

    @PostMapping
    public Estoque adicionarItem(@RequestBody Estoque item) {
        return estoqueService.adicionarItem(item);
    }
}