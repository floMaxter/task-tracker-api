package com.projects.tasktracker.scheduler.mapper;

import com.projects.tasktracker.scheduler.client.task.dto.TaskDto;
import com.projects.tasktracker.scheduler.client.user.dto.UserSummaryResponse;
import com.projects.tasktracker.scheduler.dto.UserTasksSummary;
import com.projects.tasktracker.scheduler.util.TaskFilterUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserTasksSummaryMapper {

    public UserTasksSummary toUserTasksSummary(UserSummaryResponse userSummary, List<TaskDto> tasks) {
        return UserTasksSummary.builder()
                .email(userSummary.email())
                .allTasks(tasks)
                .completedTask(TaskFilterUtils.filterCompletedTasks(tasks))
                .incompletedTask(TaskFilterUtils.filterIncompleteTasks(tasks))
                .build();
    }
}
