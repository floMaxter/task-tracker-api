package com.projects.tasktracker.gateway.config;

import com.projects.tasktracker.gateway.config.prop.SecurityPathsProperties;
import com.projects.tasktracker.gateway.security.auth.JwtReactiveAuthenticationManager;
import com.projects.tasktracker.gateway.security.auth.JwtServerAuthenticationConverter;
import com.projects.tasktracker.gateway.security.filter.JwtHeadersWebFilter;
import com.projects.tasktracker.gateway.security.handler.JwtAccessDeniedHandler;
import com.projects.tasktracker.gateway.security.handler.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationEntryPoint entryPoint;
    private final JwtAccessDeniedHandler accessDeniedHandler;
    private final SecurityPathsProperties securityPathsProperties;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                         AuthenticationWebFilter jwtAuthenticationWebFilter,
                                                         JwtHeadersWebFilter jwtHeadersWebFilter) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/api/v1/auth/**").permitAll()
                        .pathMatchers("/api/**").authenticated()
                )
                .addFilterAt(jwtAuthenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .addFilterAfter(jwtHeadersWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .exceptionHandling(exceptionHandlingSpec -> exceptionHandlingSpec
                        .authenticationEntryPoint(entryPoint)
                        .accessDeniedHandler(accessDeniedHandler))
                .build();
    }

    @Bean
    public AuthenticationWebFilter jwtAuthenticationWebFilter(JwtReactiveAuthenticationManager authManager,
                                                              JwtServerAuthenticationConverter converter,
                                                              JwtAuthenticationEntryPoint entryPoint) {
        AuthenticationWebFilter filter = new AuthenticationWebFilter(authManager);
        filter.setServerAuthenticationConverter(converter);

        filter.setRequiresAuthenticationMatcher(
                ServerWebExchangeMatchers.pathMatchers("/api/**")
        );

        filter.setAuthenticationFailureHandler((webFilterExchange, exception) ->
                entryPoint.commence(webFilterExchange.getExchange(), exception));

        return filter;
    }

    @Bean
    public JwtHeadersWebFilter jwtHeadersWebFilter() {
        return new JwtHeadersWebFilter(securityPathsProperties);
    }
}
