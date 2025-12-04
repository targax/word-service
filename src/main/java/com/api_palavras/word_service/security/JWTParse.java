package com.api_palavras.word_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class JWTParse {

    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String ROLE_AUTHORITIES = "roles"; // Padronizado com "roles"

    private JWTParse() {
        // Construtor privado para impedir instância
    }

    public static JwtTokenData parseToken(String token, String secretKey, String prefix) {
        if (token == null || token.isBlank()) {
            log.error("💥 [JWT ERROR] Token ausente ou vazio!");
            throw new IllegalArgumentException("Token ausente ou inválido.");
        }


        // Remove prefixo (ex: "Bearer ")
        if (token.startsWith(prefix + " ")) {
            token = token.substring((prefix + " ").length());
        }
        token = token.trim();

        // Cria chave secreta
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        // Parse do token
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        JwtTokenData jwtData = new JwtTokenData();
        jwtData.setSubject(claims.getSubject());
        jwtData.setIssuedAt(claims.getIssuedAt());
        jwtData.setExpiration(claims.getExpiration());

        // Extrai roles de forma segura
        jwtData.setRoles(extractRoles(claims));

        log.info("✅ [JWT SUCCESS] Token processado com sucesso para usuário: {}", jwtData.getSubject());

        return jwtData;
    }

    /**
     * Extrai roles do Claims de forma segura, evitando raw types.
     */
    private static List<String> extractRoles(Claims claims) {
        Object rolesObj = claims.get(JWTParse.ROLE_AUTHORITIES);

        if (rolesObj instanceof List<?> rawList) {
            return rawList.stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
        }

        return List.of(); // Retorna lista vazia caso não existam roles
    }
}
