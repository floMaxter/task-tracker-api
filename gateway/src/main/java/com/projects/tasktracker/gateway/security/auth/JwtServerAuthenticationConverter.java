package com.projects.tasktracker.gateway.security.auth;

import com.projects.tasktracker.gateway.security.util.AuthHeaderUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtServerAuthenticationConverter implements ServerAuthenticationConverter {

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .filter(authHeader -> authHeader.startsWith(AuthHeaderUtil.BEARER_SCHEMA))
                .map(authHeader -> authHeader.substring(AuthHeaderUtil.BEARER_SCHEMA.length()))
                .map(token -> new UsernamePasswordAuthenticationToken(null, token));
    }
}
