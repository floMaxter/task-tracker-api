package com.projects.tasktracker.auth.messaging.event;

import lombok.Builder;

@Builder
public record EmailSendingTaskMessage(
        String email,
        String title,
        String message) {
}
