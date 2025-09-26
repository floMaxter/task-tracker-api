package com.projects.tasktracker.auth.client.user.dto.request;

import lombok.Builder;

@Builder
public record CreateUserRequest(
        String username,
        String email,
        String password
) {}
