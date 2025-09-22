package com.projects.tasktracker.auth.web.mapper;

import com.projects.tasktracker.auth.web.dto.internal.SignInResult;
import com.projects.tasktracker.auth.web.dto.internal.SignUpResult;
import org.springframework.stereotype.Component;

@Component
public class AuthResultMapper {

    public SignUpResult toSignUpResult(String username, String email, String accessToken, String refreshToken) {
        return SignUpResult.builder()
                .username(username)
                .email(email)
                .jwtAccessToken(accessToken)
                .jwtRefreshToken(refreshToken)
                .build();
    }

    public SignInResult toSignInResult(Long userId, String email, String accessToken, String refreshToken) {
        return SignInResult.builder()
                .id(userId)
                .email(email)
                .jwtAccessToken(accessToken)
                .jwtRefreshToken(refreshToken)
                .build();
    }
}
