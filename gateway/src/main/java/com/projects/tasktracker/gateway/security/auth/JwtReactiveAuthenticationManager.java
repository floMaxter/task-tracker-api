package com.projects.tasktracker.gateway.security.auth;

import com.projects.tasktracker.gateway.security.jwt.JwtKeyService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.security.PublicKey;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtReactiveAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtKeyService keyService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        var accessToken = authentication.getCredentials().toString();
        return keyService.getAccessTokenPublicKey()
                .flatMap(publicKey -> validateToken(accessToken, publicKey));
    }

    private Mono<Authentication> validateToken(String token, PublicKey key) {
        try {
            var claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return Mono.just(new UsernamePasswordAuthenticationToken(
                    claims.getSubject(),
                    null,
                    List.of()
            ));
        } catch (JwtException e) {
            return Mono.error(new BadCredentialsException("Invalid JWT token", e));
        }
    }
}
