package com.eventosatleticas.controller;

import com.eventosatleticas.dto.LoginRequest;
import com.eventosatleticas.dto.LoginResponse;
import com.eventosatleticas.dto.RegistroRequest;
import com.eventosatleticas.model.Administrador;
import com.eventosatleticas.model.Arbitro;
import com.eventosatleticas.model.Organizador;
import com.eventosatleticas.model.Usuario;
import com.eventosatleticas.security.JwtUtils;
import com.eventosatleticas.security.UserDetailsImpl;
import com.eventosatleticas.service.UsuarioService;

import jakarta.validation.Valid;

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
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        logger.info("Tentativa de login - Email: " + loginRequest.getEmail() + ", Senha: " + loginRequest.getSenha());
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            logger.info("Autenticação bem-sucedida - Email: " + userDetails.getUsername() + ", Role: " + userDetails.getRole());
            String jwt = jwtUtils.generateJwtToken(userDetails);
            logger.info("Token gerado para: " + loginRequest.getEmail());
            return ResponseEntity.ok(new LoginResponse(jwt, userDetails.getId(), userDetails.getRole()));
        } catch (Exception e) {
            logger.error("Falha no login para " + loginRequest.getEmail() + ": " + e.getMessage(), e);
            return ResponseEntity.status(401).body("Credenciais inválidas");
        }
    }
    
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
    
    @GetMapping("/test-password")
    public String testPassword() {
        String rawPassword = "123456";
        String encodedPassword = "$2a$10$24Bvjo1WaHPly8QRebkNrOEcSOodxNSvz69ldw/1aka6PYryZGMRy"; 
        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
        return "Senhas coincidem: " + matches;
    }
}