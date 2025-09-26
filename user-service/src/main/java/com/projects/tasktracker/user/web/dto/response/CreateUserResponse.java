package com.projects.tasktracker.user.web.dto.response;

public record CreateUserResponse(
        Long id,
        String username,
        String email) {}
