package com.projects.tasktracker.gateway.client.auth;

import com.projects.tasktracker.gateway.client.auth.dto.response.ValidateTokenResponse;
import com.projects.tasktracker.gateway.security.util.AuthHeaderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthServiceClient {

    private final WebClient authServiceWebClient;

    public Mono<ValidateTokenResponse> validateToken(String accessToken) {
        return authServiceWebClient.post()
                .uri("api/v1/auth/validate")
                .header(HttpHeaders.AUTHORIZATION, AuthHeaderUtil.bearer(accessToken))
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        resp -> Mono.error(new BadCredentialsException("Invalid jwt token")))
                .bodyToMono(ValidateTokenResponse.class)
                .onErrorMap(e -> e instanceof AuthenticationException
                        ? e : new BadCredentialsException("Invalid JWT token", e)
                );

    }
}
