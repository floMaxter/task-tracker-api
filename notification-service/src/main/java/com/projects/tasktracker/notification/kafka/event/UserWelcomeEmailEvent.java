package com.projects.tasktracker.notification.kafka.event;

import lombok.Builder;

@Builder
public record UserWelcomeEmailEvent(
        String email,
        String title,
        String message) {
}
