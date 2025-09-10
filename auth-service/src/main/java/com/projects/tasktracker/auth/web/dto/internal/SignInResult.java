package com.projects.tasktracker.auth.web.dto.internal;

import lombok.Builder;

@Builder
public record SignInResult(
        Long id,
        String email,
        String jwtAccessToken,
        String jwtRefreshToken
) {}
