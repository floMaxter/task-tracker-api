package com.projects.tasktracker.auth.web.dto.internal;

import lombok.Builder;

@Builder
public record SignUpResult(
        String username,
        String email,
        String jwtAccessToken,
        String jwtRefreshToken
) {}
