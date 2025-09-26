package com.projects.tasktracker.auth.security.jwt;

import com.projects.tasktracker.auth.web.dto.internal.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;
    private final JwtKeyLoader jwtKeyLoader;

    @PostConstruct
    public void initJwtKeys() {
        var privateKey = jwtKeyLoader.loadPrivateKey(jwtProperties.getAccessTokenPrivateKeyPath());
        var publicKey = jwtKeyLoader.loadPublicKey(jwtProperties.getAccessTokenPublicKeyPath());

        jwtProperties.setAccessTokenPrivateKey(privateKey);
        jwtProperties.setAccessTokenPublicKey(publicKey);
        jwtProperties.setRefreshTokenPrivateKey(privateKey);
        jwtProperties.setRefreshTokenPublicKey(publicKey);
    }

    public String generateAccessToken(UserPrincipal principal) {
        var issueDate = new Date();
        var expiredDate = new Date(issueDate.getTime() + jwtProperties.getAccessTokenExpiration().toMillis());
        return Jwts.builder()
                .subject(principal.id().toString())
                .claim("email", principal.email())
                .issuedAt(issueDate)
                .expiration(expiredDate)
                .signWith(jwtProperties.getAccessTokenPrivateKey())
                .compact();
    }

    public String generateRefreshToken(UserPrincipal principal) {
        var issueDate = new Date();
        var expiredDate = new Date(issueDate.getTime() + jwtProperties.getRefreshTokenExpiration().toMillis());
        return Jwts.builder()
                .subject(principal.id().toString())
                .claim("email", principal.email())
                .issuedAt(issueDate)
                .expiration(expiredDate)
                .signWith(jwtProperties.getRefreshTokenPrivateKey())
                .compact();
    }

    public PublicKey getAccessTokenPublicKey() {
        return this.jwtProperties.getAccessTokenPublicKey();
    }

    public boolean validateAccessToken(String accessToken) {
        return validateToken(accessToken, jwtProperties.getAccessTokenPublicKey());
    }

    private boolean validateToken(String token, PublicKey secret) {
        try {
            Jwts.parser()
                    .verifyWith(secret)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.info("Token expired: {}", expEx.getMessage());
        } catch (UnsupportedJwtException unsEx) {
            log.info("Unsupported jwt: {}", unsEx.getMessage());
        } catch (MalformedJwtException mjEx) {
            log.info("Malformed jwt: {}", mjEx.getMessage());
        } catch (SignatureException sEx) {
            log.info("Invalid signature: {}", sEx.getMessage());
        } catch (Exception e) {
            log.error("Unexpected JWT validation error", e);
        }
        return false;
    }

    public String getEmail(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(jwtProperties.getAccessTokenPublicKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
