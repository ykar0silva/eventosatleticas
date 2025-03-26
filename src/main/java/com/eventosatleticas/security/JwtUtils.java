package com.eventosatleticas.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(UserDetailsImpl userDetails) {
        logger.info("Gerando token JWT para: " + userDetails.getUsername());
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("role", userDetails.getRole())
                .claim("userId", userDetails.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS256, jwtSecret.getBytes())
                .compact();
    }

    public String getEmailFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret.getBytes())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Long getUserIdFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret.getBytes())
                .parseClaimsJws(token)
                .getBody()
                .get("userId", Long.class);
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtSecret.getBytes())
                    .parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            logger.error("Token JWT inválido: " + e.getMessage());
            return false;
        }
    }
}