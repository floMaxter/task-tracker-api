package com.projects.tasktracker.auth.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;

    public String generateAccessToken(String email) {
        var issueDate = new Date();
        var expiredDate = new Date(issueDate.getTime() + jwtProperties.getAccessToken().getExpiration().toMillis());
        return Jwts.builder()
                .subject(email)
                .issuedAt(issueDate)
                .expiration(expiredDate)
                .signWith(getJwtAccessSecret())
                .compact();
    }

    public String generateRefreshToken(String email) {
        var issueDate = new Date();
        var expiredDate = new Date(issueDate.getTime() + jwtProperties.getRefreshToken().getExpiration().toMillis());
        return Jwts.builder()
                .subject(email)
                .issuedAt(issueDate)
                .expiration(expiredDate)
                .signWith(getJwtRefreshSecret())
                .compact();
    }

    public String getEmail(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getJwtAccessSecret())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getJwtAccessSecret() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getAccessToken().getSecret()));
    }

    private SecretKey getJwtRefreshSecret() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getRefreshToken().getSecret()));
    }
}
