package com.projects.tasktracker.auth.client.user.dto.response;

public record UserCredentialsResponse(
        Long id,
        String email,
        String password) {}
