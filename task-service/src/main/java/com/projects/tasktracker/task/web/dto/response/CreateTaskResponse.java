package com.projects.tasktracker.task.web.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.projects.tasktracker.task.domain.TaskStatus;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreateTaskResponse(
        Long id,
        String title,
        String description,
        TaskStatus status,
        Long ownerId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime completedAt) {
}
