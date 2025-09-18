package com.projects.tasktracker.gateway.security.filter;

import com.projects.tasktracker.gateway.config.prop.SecurityPathsProperties;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class JwtHeadersWebFilter implements WebFilter {

    private final SecurityPathsProperties securityPathsProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        var path = exchange.getRequest().getPath().value();

        if (isWhitelisted(path)) {
            return chain.filter(exchange);
        }

        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .flatMap(auth -> {
                    if (auth != null && auth.isAuthenticated()) {
                        var sub = (String) auth.getPrincipal();
                        var claims = (Claims) auth.getDetails();


                        var mutated = exchange.mutate()
                                .request(r -> r.headers(h -> {
                                            h.set("X-User-Id", sub);
                                            claims.forEach((k, v) -> h.set("X-Claim-" + k, String.valueOf(v)));
                                        })
                                )
                                .build();

                        return chain.filter(mutated);
                    } else {
                        return chain.filter(exchange);
                    }
                });
    }

    private boolean isWhitelisted(String path) {
        return securityPathsProperties.getWhitelist().stream()
                .anyMatch(path::startsWith);
    }
}
