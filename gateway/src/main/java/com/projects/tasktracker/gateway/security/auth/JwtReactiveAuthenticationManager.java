package com.projects.tasktracker.gateway.security.auth;

import com.projects.tasktracker.gateway.client.auth.AuthServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtReactiveAuthenticationManager implements ReactiveAuthenticationManager {

    private final AuthServiceClient authServiceClient;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        var accessToken = authentication.getCredentials().toString();

        return authServiceClient.validateToken(accessToken)
                .map(validateResponse -> (Authentication) new UsernamePasswordAuthenticationToken(
                        validateResponse.email(),
                        null,
                        List.of()
                ))
                .onErrorMap(e -> {
                    if (e instanceof AuthenticationException) return e;
                    return new BadCredentialsException("Invalid JWT token", e);
                });
    }
}
