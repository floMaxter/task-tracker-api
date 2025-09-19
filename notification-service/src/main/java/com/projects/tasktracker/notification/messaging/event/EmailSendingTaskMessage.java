package com.projects.tasktracker.notification.messaging.event;

import lombok.Builder;

@Builder
public record EmailSendingTaskMessage(
        String email,
        String title,
        String message) {
}
