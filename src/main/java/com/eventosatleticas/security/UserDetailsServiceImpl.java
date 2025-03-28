package com.eventosatleticas.security;

import com.eventosatleticas.model.Usuario;
import com.eventosatleticas.service.UsuarioService;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOpt = usuarioService.findByEmail(username);
        if (usuarioOpt.isEmpty()) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + username);
        }
        
        Usuario usuario = usuarioOpt.get();
        return new org.springframework.security.core.userdetails.User(
            usuario.getEmail(),
            usuario.getSenha(),
            usuario.getAuthorities()
        );
    }
}