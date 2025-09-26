package com.projects.tasktracker.user.web.dto.response;

public record UserCredentialsResponse(
        Long id,
        String email,
        String password) {}
