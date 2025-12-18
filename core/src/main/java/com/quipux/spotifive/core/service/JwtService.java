package com.quipux.spotifive.core.service;

import com.quipux.spotifive.common.constants.ErrorMessages;
import com.quipux.spotifive.common.enums.TokenType;
import com.quipux.spotifive.core.components.I18NComponent;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class JwtService {

    private final I18NComponent i18NComponent;

    private final SecretKey JWT_ACCESS_SECRET;
    private final SecretKey JWT_REFRESH_SECRET;

    public JwtService(
            @Value("${jwt.secret}") String secretKey, I18NComponent i18NComponent) {
        this.i18NComponent = i18NComponent;

        byte[] keyBytes = secretKey.getBytes();

        this.JWT_ACCESS_SECRET = Keys.hmacShaKeyFor(keyBytes);
        this.JWT_REFRESH_SECRET = Keys.hmacShaKeyFor(keyBytes);
    }

    private SecretKey getSecretKey(TokenType type) {
        return switch (type) {
            case ACCESS -> JWT_ACCESS_SECRET;
            case REFRESH -> JWT_REFRESH_SECRET;
            default -> throw new IllegalArgumentException("No type token: " + type);
        };
    }

    public Long getUserIdFromToken(String token, TokenType type) {
        log.info("JwtService::getUserIdFromToken");
        SecretKey key = getSecretKey(type);
        return Long.valueOf(Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject());
    }

    public String generateToken(Long userId, long expiration, TokenType type) {
        Map<String, Object> claims = new HashMap<>();
        claims = Jwts.claims().setSubject(userId.toString());
        Date now = new Date();
        Date expDate = new Date(now.getTime() + expiration);
        SecretKey key = getSecretKey(type);
        return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(expDate).signWith(key).compact();
    }

    public Date getExpirationDateFromToken(String token, TokenType type) {
        log.info("JwtService::getExpirationDateFromToken");
        SecretKey key = getSecretKey(type);
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration();
    }

    public Boolean isTokenExpired(String token, TokenType type) {
        log.info("JwtService::isTokenExpired");
        final Date expiration = getExpirationDateFromToken(token, type);
        return expiration.before(new Date());
    }

    public Boolean validate(String token, TokenType type) {
        log.info("JwtService::validate");
        try {
            SecretKey key = getSecretKey(type);
            if (token.isEmpty())
                throw new JwtException(i18NComponent.getMessage(
                        ErrorMessages.INVALID_TOKEN));
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return !isTokenExpired(token, type);
        } catch (ExpiredJwtException e){
            throw new JwtException(i18NComponent.getMessage(
                    ErrorMessages.TOKEN_UNAUTHORIZED));

        } catch (Exception e) {
            throw new JwtException(e.getMessage());
        }
    }
}
