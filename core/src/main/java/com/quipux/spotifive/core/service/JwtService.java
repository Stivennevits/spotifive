package com.quipux.spotifive.core.service;

import com.quipux.spotifive.common.enums.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String generateToken(Long userId, Long expirationTimeInMillis, TokenType tokenType) {
        log.info("JwtService::generateToken userId: {}, expirationTime: {}, tokenType: {}", userId, expirationTimeInMillis, tokenType);

        try {
            Date now = new Date();
            Date expirationDate = new Date(now.getTime() + expirationTimeInMillis);

            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

            String token = Jwts.builder()
                    .setSubject(String.valueOf(userId))
                    .claim("userId", String.valueOf(userId))
                    .claim("tokenType", tokenType.name())
                    .setIssuedAt(now)
                    .setExpiration(expirationDate)
                    .signWith(key)
                    .compact();

            log.info("JwtService::generateToken Token generated successfully for user: {}", userId);
            return token;

        } catch (Exception e) {
            log.error("JwtService::generateToken Error generating token for user: {}", userId, e);
            throw new RuntimeException("Error generando el token JWT", e);
        }
    }

    public Long extractUserIdFromToken(String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String userIdStr = claims.get("userId", String.class);
            if (userIdStr == null) {
                userIdStr = claims.getSubject();
            }

            return Long.parseLong(userIdStr);

        } catch (Exception e) {
            log.error("JwtService::extractUserIdFromToken Error extracting user ID from token", e);
            throw new RuntimeException("Token inválido o expirado");
        }
    }

    public boolean isTokenValid(String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error("JwtService::isTokenValid Token validation failed", e);
            return false;
        }
    }
}
