package com.eventosatleticas.security;

import com.eventosatleticas.model.Usuario;
import com.eventosatleticas.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class); // Correção aqui

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.info("Carregando usuário para email: " + email);
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.error("Usuário não encontrado: " + email);
                    return new UsernameNotFoundException("Usuário não encontrado: " + email);
                });
        logger.info("Usuário carregado - Email: " + usuario.getEmail() + ", Senha: '" + usuario.getSenha() + "', Role: " + usuario.getRole());
        return new UserDetailsImpl(usuario);
    }
}