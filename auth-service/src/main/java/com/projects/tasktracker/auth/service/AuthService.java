package com.projects.tasktracker.auth.service;

import com.projects.tasktracker.auth.client.user.UserServiceClient;
import com.projects.tasktracker.auth.client.user.dto.request.CreateUserRequest;
import com.projects.tasktracker.auth.kafka.producer.AuthEventProducer;
import com.projects.tasktracker.auth.security.jwt.JwtService;
import com.projects.tasktracker.auth.web.dto.internal.SignInResult;
import com.projects.tasktracker.auth.web.dto.internal.SignUpResult;
import com.projects.tasktracker.auth.web.dto.internal.UserPrincipal;
import com.projects.tasktracker.auth.web.dto.request.SignInRequest;
import com.projects.tasktracker.auth.web.dto.request.SignUpRequest;
import com.projects.tasktracker.auth.web.dto.response.PublicKeyResponse;
import com.projects.tasktracker.core.kafka.event.UserWelcomeEmailEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserServiceClient userServiceClient;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AuthEventProducer authEventProducer;

    public SignUpResult signUp(SignUpRequest signUpRequest) {
        var createdUser = userServiceClient.createUser(CreateUserRequest.builder()
                .username(signUpRequest.username())
                .email(signUpRequest.email())
                .password(signUpRequest.password())
                .build()
        );

        var userPrincipal = new UserPrincipal(createdUser.id(), createdUser.email());
        var accessToken = jwtService.generateAccessToken(userPrincipal);
        var refreshToken = jwtService.generateRefreshToken(userPrincipal);

        authEventProducer.sendUserRegisteredEvent(UserWelcomeEmailEvent.builder()
                        .email(createdUser.email())
                        .title("Task tracker greeting")
                        .message(String.format("Hello, %s! Thank you for choosing to use our task tracker.", createdUser.username()))
                .build());

//        authEventProducer.sendUserRegisteredEvent(new UserWelcomeEmailEvent(
//                createdUser.email(),
//                "Task tracker greeting",
//                String.format("Hello, %s! Thank you for choosing to use our task tracker.", createdUser.username())));

        return SignUpResult.builder()
                .username(createdUser.username())
                .email(createdUser.email())
                .jwtAccessToken(accessToken)
                .jwtRefreshToken(refreshToken)
                .build();
    }

    public SignInResult signIn(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.email(), signInRequest.password()));

        var userSummary = userServiceClient.getUserSummaryByEmail(signInRequest.email());

        var userPrincipal = new UserPrincipal(userSummary.id(), userSummary.email());
        var accessToken = jwtService.generateAccessToken(userPrincipal);
        var refreshToken = jwtService.generateRefreshToken(userPrincipal);

        return SignInResult.builder()
                .id(userSummary.id())
                .email(userSummary.email())
                .jwtAccessToken(accessToken)
                .jwtRefreshToken(refreshToken)
                .build();
    }

    public PublicKeyResponse getAccessTokenPublicKey() {
        var publicKey = jwtService.getAccessTokenPublicKey();
        var encodedKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());

        return new PublicKeyResponse(encodedKey);
    }
}
