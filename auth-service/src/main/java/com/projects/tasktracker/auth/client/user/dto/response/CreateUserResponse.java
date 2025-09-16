package com.projects.tasktracker.auth.client.user.dto.response;

public record CreateUserResponse(
        Long id,
        String username,
        String email
) {}
