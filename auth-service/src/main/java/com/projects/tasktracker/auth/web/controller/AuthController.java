package com.projects.tasktracker.auth.web.controller;

import com.projects.tasktracker.auth.service.AuthService;
import com.projects.tasktracker.auth.util.TokenResponseUtil;
import com.projects.tasktracker.auth.web.dto.request.SignInRequest;
import com.projects.tasktracker.auth.web.dto.request.SignUpRequest;
import com.projects.tasktracker.auth.web.dto.response.SignInResponse;
import com.projects.tasktracker.auth.web.dto.response.SignUpResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest,
                                                 HttpServletResponse resp) {
        var signUpResult = authService.signUp(signUpRequest);

        var headers = new HttpHeaders();
        TokenResponseUtil.addTokens(headers, resp, signUpResult.jwtAccessToken(), signUpResult.jwtRefreshToken());

        var signUpResponse = new SignUpResponse(signUpRequest.username(), signUpRequest.email());

        return ResponseEntity.ok()
                .headers(headers)
                .body(signUpResponse);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponse> signIn(@Valid @RequestBody SignInRequest signInRequest,
                                                 HttpServletResponse resp) {
        var signInResult = authService.signIn(signInRequest);

        var headers = new HttpHeaders();
        TokenResponseUtil.addTokens(headers, resp, signInResult.jwtAccessToken(), signInResult.jwtRefreshToken());

        var signInResponse = new SignInResponse(signInResult.id(), signInResult.email());

        return ResponseEntity.ok()
                .headers(headers)
                .body(signInResponse);
    }
}
