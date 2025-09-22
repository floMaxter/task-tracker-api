package com.projects.tasktracker.core.kafka.event;

import lombok.Builder;

@Builder
public record EmailSendingTask(
        String email,
        String title,
        String message) {
}
