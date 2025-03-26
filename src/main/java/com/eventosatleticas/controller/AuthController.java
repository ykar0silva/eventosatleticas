package com.eventosatleticas.controller;

import com.eventosatleticas.dto.RegistroRequest;
import com.eventosatleticas.model.Administrador;
import com.eventosatleticas.model.Arbitro;
import com.eventosatleticas.model.Organizador;
import com.eventosatleticas.model.Usuario;
import com.eventosatleticas.service.UsuarioService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@Valid @RequestBody RegistroRequest request) {
        logger.info("Requisição recebida para registrar: " + request.getEmail());
        try {
            if (usuarioService.emailExistente(request.getEmail())) {
                logger.info("Email já cadastrado: " + request.getEmail());
                return ResponseEntity.badRequest().body("Email já cadastrado");
            }

            Usuario novoUsuario;
            switch (request.getTipoUsuario().toUpperCase()) {
                case "ADMIN":
                    novoUsuario = new Administrador();
                    break;
                case "ORGANIZADOR":
                    novoUsuario = new Organizador();
                    break;
                case "ARBITRO":
                    novoUsuario = new Arbitro();
                    break;
                default:
                    logger.warn("Tipo de usuário inválido: " + request.getTipoUsuario());
                    return ResponseEntity.badRequest().body("Tipo de usuário inválido");
            }

            novoUsuario.setEmail(request.getEmail());
            novoUsuario.setSenha(passwordEncoder.encode(request.getSenha()));
            novoUsuario.setRole(request.getTipoUsuario().toUpperCase());

            Usuario usuarioSalvo = usuarioService.registrar(novoUsuario);
            logger.info("Usuário salvo: " + usuarioSalvo.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Usuário registrado com sucesso: " + usuarioSalvo.getEmail());
        } catch (Exception e) {
            logger.error("Erro ao registrar usuário: " + e.getMessage());
            return ResponseEntity.badRequest().body("Erro no registro: " + e.getMessage());
        }
    }
}