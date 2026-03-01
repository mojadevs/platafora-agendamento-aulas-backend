package com.api.demo.jwt;
import com.api.demo.enums.usuario.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtServices {

    private static final String SECRET = "sua-chave-super-secreta-com-32-bytes-minimo";
    private static final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());// guarde em config segura
    private static final long EXPIRATION = 1000 * 60 * 60 * 2; // 2 horas

    // Gerar token
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key)
                .compact();
    }

    public String getRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    public boolean isTokenValid(String token) {
        try {
            getClaims(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    // Extrair email do token
    public String getEmail(String token) {
        return getClaims(token).getSubject();
    }

    // Verificar expiração
    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }
}
