package com.projects.tasktracker.auth.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
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

    public boolean validateAccessToken(String accessToken) {
        return validateToken(accessToken, this.getJwtAccessSecret());
    }

    private boolean validateToken(String token, SecretKey secret) {
        try {
            Jwts.parser()
                    .verifyWith(secret)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
        } catch (SignatureException sEx) {
            log.error("Invalid signature", sEx);
        } catch (Exception e) {
            log.error("Invalid token", e);
        }
        return false;
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
