package com.projects.tasktracker.auth.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.security.PublicKey;
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
        private String privateKeyPath;
        private String publicKeyPath;
        private Duration expiration;

        private PrivateKey privateKey;
        private PublicKey publicKey;
    }

    public String getAccessTokenPrivateKeyPath() {
        return this.accessToken.privateKeyPath;
    }

    public String getAccessTokenPublicKeyPath() {
        return this.accessToken.publicKeyPath;
    }

    public String getRefreshTokenPrivateKeyPath() {
        return this.refreshToken.privateKeyPath;
    }

    public String getRefreshTokenPublicKeyPath() {
        return this.refreshToken.publicKeyPath;
    }

    public Duration getAccessTokenExpiration() {
        return this.accessToken.expiration;
    }

    public Duration getRefreshTokenExpiration() {
        return this.accessToken.expiration;
    }

    public PrivateKey getAccessTokenPrivateKey() {
        return this.accessToken.privateKey;
    }

    public PublicKey getAccessTokenPublicKey() {
        return this.accessToken.publicKey;
    }

    public PrivateKey getRefreshTokenPrivateKey() {
        return this.refreshToken.privateKey;
    }

    public PublicKey getRefreshTokenPublicKey() {
        return this.refreshToken.publicKey;
    }

    public void setAccessTokenPrivateKey(PrivateKey privateKey) {
        this.accessToken.privateKey = privateKey;
    }

    public void setAccessTokenPublicKey(PublicKey publicKey) {
        this.accessToken.publicKey = publicKey;
    }

    public void setRefreshTokenPrivateKey(PrivateKey privateKey) {
        this.refreshToken.privateKey = privateKey;
    }

    public void setRefreshTokenPublicKey(PublicKey publicKey) {
        this.refreshToken.publicKey = publicKey;
    }
}
