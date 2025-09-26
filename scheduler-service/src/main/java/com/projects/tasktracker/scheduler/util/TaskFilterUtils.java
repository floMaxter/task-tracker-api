package com.projects.tasktracker.scheduler.util;

import com.projects.tasktracker.scheduler.client.task.dto.TaskDto;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.List;

@UtilityClass
public class TaskFilterUtils {

    public List<TaskDto> filterTasksCompletedInLast24h(List<TaskDto> tasks) {
        var nowTime = LocalDateTime.now();
        var yesterdayTime = nowTime.minusDays(1);
        return tasks.stream()
                .filter(task -> task.completedAt() != null)
                .filter(task -> !task.completedAt().isBefore(yesterdayTime) && !task.completedAt().isAfter(nowTime))
                .toList();
    }

    public List<TaskDto> filterIncompleteTasks(List<TaskDto> tasks) {
        return tasks.stream()
                .filter(task -> task.completedAt() == null)
                .toList();
    }

    public List<TaskDto> filterCompletedTasks(List<TaskDto> tasks) {
        return tasks.stream()
                .filter(task -> task.completedAt() != null)
                .toList();
    }
}
