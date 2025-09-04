package com.projects.tasktracker.user.web.dto.response;

public record UserResponse(
        Long id,

        String username,

        String email,

        String password) {
}
