package com.projects.tasktracker.gateway.security.jwt;

import com.projects.tasktracker.gateway.client.auth.AuthServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.time.Duration;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtKeyService {

    private final AuthServiceClient authServiceClient;
    private final AtomicReference<PublicKey> cachedKey = new AtomicReference<>();

    public Mono<PublicKey> getAccessTokenPublicKey() {
        PublicKey key = cachedKey.get();
        if (key != null) {
            return Mono.just(key);
        }

        return authServiceClient.getAccessPublicKey()
                .map(response -> decodePublicKey(response.key()))
                .doOnNext(cachedKey::set)
                .retryWhen(Retry.backoff(5, Duration.ofSeconds(1))
                        .onRetryExhaustedThrow((spec, signal) ->
                                new IllegalStateException("Cannot load public key from auth-service", signal.failure()))
                )
                .onErrorResume(e -> {
                    log.warn("Failed to load public key from auth-service, will retry on next request", e);
                    PublicKey cached = cachedKey.get();
                    return cached != null ? Mono.just(cached) : Mono.error(e);
                });
    }

    private PublicKey decodePublicKey(String base64Key) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(base64Key);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(spec);
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to decode public key", ex);
        }
    }
}
