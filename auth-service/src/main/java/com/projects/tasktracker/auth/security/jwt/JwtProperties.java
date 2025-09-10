package com.projects.tasktracker.auth.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@ConfigurationProperties(prefix = "application.jwt")
@Getter
@Setter
public class JwtProperties {

    private TokenProperties accessToken;
    private TokenProperties refreshToken;

    @Getter
    @Setter
    public static class TokenProperties {
        String secret;
        Duration expiration;
    }
}
