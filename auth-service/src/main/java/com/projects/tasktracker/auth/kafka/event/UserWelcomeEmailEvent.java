package com.projects.tasktracker.auth.kafka.event;

import lombok.Builder;

@Builder
public record UserWelcomeEmailEvent(
        String email,
        String title,
        String message) {
}
