package com.escomipn.backendapi.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Esta es la firma secreta de tu servidor (Debe ser larga y compleja)
    private static final String SECRET = "EstaEsLaClaveSecretaDeLaPracticaDeMobilesIPN123456789";
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // Método para crear el Token
    public String generarToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // El token dura 10 horas
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Método para leer el correo que viene dentro del Token
    public String extraerEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    // Método para verificar que el Token no sea falso ni esté caducado
    public boolean validarToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}