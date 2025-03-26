package com.eventosatleticas.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.eventosatleticas.model.Usuario;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {
    private final Long id;
    private final String username;
    private final String password;
    private final String role;

    // Construtor existente
    public UserDetailsImpl(Long id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Novo construtor que aceita um objeto Usuario
    public UserDetailsImpl(Usuario usuario) {
        this.id = usuario.getId();
        this.username = usuario.getEmail(); // Usamos o email como username
        this.password = usuario.getSenha();
        this.role = usuario.getRole();
    }

    public Long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(() -> "ROLE_" + role);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}