package com.eventosatleticas.controller;

import com.eventosatleticas.model.Atletica;
import com.eventosatleticas.service.AtleticaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atleticas")
public class AtleticaController {

    @Autowired
    private AtleticaService atleticaService;

    @GetMapping
    public List<Atletica> listarAtléticas() {
        return atleticaService.listarTodas();
    }

    @PostMapping
    public Atletica criarAtlética(@RequestBody Atletica atletica) {
        return atleticaService.criarAtlética(atletica);
    }
}