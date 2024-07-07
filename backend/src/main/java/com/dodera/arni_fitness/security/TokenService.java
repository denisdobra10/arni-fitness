package com.dodera.arni_fitness.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
public class TokenService {
    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    @Resource
    private Properties properties;

    public TokenService() {}

    public Optional<Authentication> getAuthenticationForJwt(String jwtToken) {
        Claims claims = this.extractAllClaims(jwtToken);
        return Optional.of(new UsernamePasswordAuthenticationToken(claims.getSubject(), null, null));
    }

    public String generateToken(String email) {
        logger.info("Generate JWT Token for user: {}", email);
        var claims = Jwts.claims().setSubject(email);

        return Jwts
                .builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + properties.getExpirationTime()))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(properties.getJwtSigningKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).get("sub", String.class);
    }

    public Map<String, Object> extractClaims(String token) {
        return extractAllClaims(token);
    }

    @ConfigurationProperties(prefix = "dodera.token")
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static final class Properties {
        private String jwtSigningKey;
        private Long expirationTime;

        public String getJwtSigningKey() {
            return jwtSigningKey;
        }

        public void setJwtSigningKey(String jwtSigningKey) {
            this.jwtSigningKey = jwtSigningKey;
        }

        public Long getExpirationTime() {
            return expirationTime;
        }

        public void setExpirationTime(Long sessionTtl) {
            this.expirationTime = sessionTtl;
        }
    }
}
