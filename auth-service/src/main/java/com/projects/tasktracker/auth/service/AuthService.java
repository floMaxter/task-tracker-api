package com.projects.tasktracker.auth.service;

import com.projects.tasktracker.auth.client.user.UserServiceClient;
import com.projects.tasktracker.auth.client.user.dto.request.CreateUserRequest;
import com.projects.tasktracker.auth.security.jwt.JwtService;
import com.projects.tasktracker.auth.web.dto.internal.SignInResult;
import com.projects.tasktracker.auth.web.dto.internal.SignUpResult;
import com.projects.tasktracker.auth.web.dto.request.SignInRequest;
import com.projects.tasktracker.auth.web.dto.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserServiceClient userServiceClient;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public SignUpResult signUp(SignUpRequest signUpRequest) {
        var createdUser = userServiceClient.createUser(CreateUserRequest.builder()
                .username(signUpRequest.username())
                .email(signUpRequest.email())
                .password(signUpRequest.password())
                .build()
        );
        var accessToken = jwtService.generateAccessToken(createdUser.email());
        var refreshToken = jwtService.generateRefreshToken(createdUser.email());

        return SignUpResult.builder()
                .username(createdUser.username())
                .email(createdUser.email())
                .jwtAccessToken(accessToken)
                .jwtRefreshToken(refreshToken)
                .build();
    }

    public SignInResult signIn(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.email(), signInRequest.password()));

        var accessToken = jwtService.generateAccessToken(signInRequest.email());
        var refreshToken = jwtService.generateRefreshToken(signInRequest.email());

        var userSummary = userServiceClient.getUserSummaryByEmail(signInRequest.email());

        return SignInResult.builder()
                .id(userSummary.id())
                .email(userSummary.email())
                .jwtAccessToken(accessToken)
                .jwtRefreshToken(refreshToken)
                .build();
    }
}
