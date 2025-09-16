package com.projects.tasktracker.gateway.security.filter;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class JwtHeadersWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
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
}
