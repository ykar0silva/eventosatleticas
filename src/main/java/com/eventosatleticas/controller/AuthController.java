package com.eventosatleticas.controller;

import com.eventosatleticas.dto.LoginRequest;
import com.eventosatleticas.dto.LoginResponse;
import com.eventosatleticas.dto.RegistroRequest;
import com.eventosatleticas.model.Administrador;
import com.eventosatleticas.model.Arbitro;
import com.eventosatleticas.model.Organizador;
import com.eventosatleticas.model.Usuario;
import com.eventosatleticas.security.JwtUtils;
import com.eventosatleticas.service.UsuarioService;

import jakarta.validation.Valid;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtils jwtUtils;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            logger.info("Tentativa de login - Email: {}, Senha: {}", loginRequest.getEmail(), loginRequest.getSenha());
            
            Optional<Usuario> usuarioOpt = usuarioService.findByEmail(loginRequest.getEmail());
            if (usuarioOpt.isEmpty()) {
                logger.error("Usuário não encontrado para o email: {}", loginRequest.getEmail());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
            }
            
            Usuario usuario = usuarioOpt.get();
            boolean passwordMatches = passwordEncoder.matches(loginRequest.getSenha(), usuario.getSenha());
            logger.info("Comparação de senha direta - Coincide: {}", passwordMatches);
            
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(), 
                    loginRequest.getSenha()
                )
            );
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            
            String token = jwtUtils.generateToken(userDetails.getUsername());
            logger.info("Login bem-sucedido para: {}", loginRequest.getEmail());
            
            return ResponseEntity.ok(new LoginResponse(token));
        } catch (BadCredentialsException e) {
            logger.error("Falha no login para {}: Bad credentials", loginRequest.getEmail(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        } catch (Exception e) {
            logger.error("Falha no login para {}: {}", loginRequest.getEmail(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no servidor");
        }
    }
    
    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@Valid @RequestBody RegistroRequest request) {
        logger.info("Requisição recebida para registrar: {}", request.getEmail());
        try {
            if (usuarioService.emailExistente(request.getEmail())) {
                logger.info("Email já cadastrado: {}", request.getEmail());
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
                    logger.warn("Tipo de usuário inválido: {}", request.getTipoUsuario());
                    return ResponseEntity.badRequest().body("Tipo de usuário inválido");
            }

            novoUsuario.setEmail(request.getEmail());
            novoUsuario.setSenha(passwordEncoder.encode(request.getSenha())); 
            novoUsuario.setRole(request.getTipoUsuario().toUpperCase());

            Usuario usuarioSalvo = usuarioService.registrar(novoUsuario);
            logger.info("Usuário salvo: {}", usuarioSalvo.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Usuário registrado com sucesso: " + usuarioSalvo.getEmail());
        } catch (Exception e) {
            logger.error("Erro ao registrar usuário: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Erro no registro: " + e.getMessage());
        }
    }
    
    @GetMapping("/test-password")
    public ResponseEntity<String> testPassword(@RequestParam String email, @RequestParam String rawPassword) {
        logger.info("Testando senha para o email: {}", email);
        Optional<Usuario> usuarioOpt = usuarioService.findByEmail(email);
        if (usuarioOpt.isEmpty()) {
            logger.error("Usuário não encontrado para o email: {}", email);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado para o email: " + email);
        }
        Usuario usuario = usuarioOpt.get();
        String senhaDoBanco = usuario.getSenha();
        boolean matches = passwordEncoder.matches(rawPassword, senhaDoBanco);
        logger.info("Senha fornecida: {} | Senha no banco: {} | Coincidem: {}", rawPassword, senhaDoBanco, matches);

        String response = String.format(
            "Senha inserida: %s | Senha do banco: %s | Coincidem: %s",
            rawPassword, senhaDoBanco, matches
        );
        return ResponseEntity.ok(response);
    }
    @GetMapping("/test-auth")
    public ResponseEntity<?> testAuth(Authentication authentication) {
    	if (authentication == null || !authentication.isAuthenticated()) {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Não autenticado");
    }
    	return ResponseEntity.ok("Autenticado como: " + authentication.getName());
}

    @GetMapping("/test-security")
    	public ResponseEntity<?> testSecurity() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	if (auth == null || !auth.isAuthenticated()) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Não autenticado");
    }
    	return ResponseEntity.ok("Autenticado como: " + auth.getName());
    }
}