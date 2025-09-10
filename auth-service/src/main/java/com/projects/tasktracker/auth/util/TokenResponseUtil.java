package com.projects.tasktracker.auth.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;

@UtilityClass
public class TokenResponseUtil {

    public void addTokens(HttpHeaders headers,
                          HttpServletResponse resp,
                          String jwtAccessToken,
                          String jwtRefreshToken) {
        addAccessTokenHeader(headers, jwtAccessToken);
        addRefreshTokenCookie(resp, jwtRefreshToken);
    }

    public void addAccessTokenHeader(HttpHeaders headers, String accessToken) {
        headers.setBearerAuth(accessToken);
    }

    public void addRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        var cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);
    }
}