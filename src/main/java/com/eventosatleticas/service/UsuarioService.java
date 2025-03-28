package com.eventosatleticas.service;

import com.eventosatleticas.model.Usuario;
import com.eventosatleticas.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario registrar(Usuario usuario) {
        logger.info("Registrando usuário: {}", usuario.getEmail());
        return usuarioRepository.save(usuario);
    }

    public boolean emailExistente(String email) {
        logger.info("Verificando se o email existe: {}", email);
        return usuarioRepository.existsByEmail(email);
    }

    public Optional<Usuario> findByEmail(String email) {
        logger.info("Buscando usuário pelo email: {}", email);
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (usuario.isPresent()) {
            logger.info("Usuário encontrado: {}", usuario.get().getEmail());
        } else {
            logger.info("Nenhum usuário encontrado para o email: {}", email);
        }
        return usuario;
    }
}