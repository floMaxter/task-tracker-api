package com.projects.tasktracker.auth.client.user.dto.response;

public record CreateUserResponse(
        String username,
        String email
) {}
