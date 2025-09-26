package com.projects.tasktracker.scheduler.dto;

import com.projects.tasktracker.scheduler.client.task.dto.TaskDto;
import lombok.Builder;

import java.util.List;

@Builder
public record UserTasksSummary(
        String email,
        List<TaskDto> allTasks,
        List<TaskDto> completedTask,
        List<TaskDto> incompletedTask) {
}
