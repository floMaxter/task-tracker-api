package com.projects.tasktracker.auth.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.WebUtils;

@UtilityClass
public class JwtTokenUtil {

    private static final String BEARER_PREFIX = "Bearer ";

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

    public String extractAccessToken(HttpServletRequest req) {
        var authHeader = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
            return authHeader.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    public String extractRefreshToken(HttpServletRequest req) {
        var cookie = WebUtils.getCookie(req, "refreshToken");
        return cookie != null ? cookie.getValue() : null;
    }
}