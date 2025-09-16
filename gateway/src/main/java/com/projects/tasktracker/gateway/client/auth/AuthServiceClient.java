package com.projects.tasktracker.gateway.client.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.projects.tasktracker.gateway.client.auth.dto.response.PublicKeyResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthServiceClient {

    private final WebClient authServiceWebClient;

    public Mono<PublicKeyResponse> getAccessPublicKey() {
        return authServiceWebClient.get()
                .uri("/api/v1/auth/public-keys/access")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        resp -> Mono.error(new IllegalArgumentException(
                                "Auth-service returned 4xx error while fetching public key: " + resp.statusCode())))
                .onStatus(HttpStatusCode::is5xxServerError,
                        resp -> Mono.error(new IllegalStateException(
                                "Auth-service returned 5xx error while fetching public key: " + resp.statusCode())))
                .bodyToMono(PublicKeyResponse.class)
                .timeout(Duration.ofSeconds(5))
                .onErrorMap(TimeoutException.class,
                        e -> new IllegalStateException("Timeout while fetching public key from auth-service", e))
                .onErrorMap(WebClientRequestException.class,
                        e -> new IllegalStateException("Failed to reach auth-service", e))
                .onErrorMap(JsonProcessingException.class,
                        e -> new IllegalArgumentException("Invalid response from auth-service", e));
    }
}
