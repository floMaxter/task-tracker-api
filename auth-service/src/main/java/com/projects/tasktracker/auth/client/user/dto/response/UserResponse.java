package com.projects.tasktracker.auth.client.user.dto.response;

public record UserResponse(
        Long id,
        String username,
        String email) {
}
