package com.projects.tasktracker.task.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateTaskRequest(

        @NotBlank(message = "Title cannot be empty")
        String title,

        String description) {
}
