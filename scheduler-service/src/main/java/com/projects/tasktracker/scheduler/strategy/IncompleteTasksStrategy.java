package com.projects.tasktracker.scheduler.strategy;

import com.projects.tasktracker.core.kafka.event.EmailSendingTask;
import com.projects.tasktracker.scheduler.client.task.dto.TaskDto;
import com.projects.tasktracker.scheduler.dto.UserTasksSummary;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class IncompleteTasksStrategy implements TaskReportStrategy {

    @Override
    public boolean isApplicable(UserTasksSummary userTasksSummary) {
        return !userTasksSummary.incompletedTask().isEmpty()
                & userTasksSummary.completedTask().isEmpty();
    }

    @Override
    public EmailSendingTask buildEmailSendingTask(UserTasksSummary userTasksSummary) {
        var countIncompletedTasks = userTasksSummary.incompletedTask().size();
        var emailTitle = String.format("You have %d incompleted tasks", countIncompletedTasks);

        var taskTitles = userTasksSummary.incompletedTask().stream()
                .limit(5)
                .map(TaskDto::title)
                .collect(Collectors.joining("\n"));
        var emailMessage = String.format("Here are a few of them:\n%s", taskTitles);

        return EmailSendingTask.builder()
                .email(userTasksSummary.email())
                .title(emailTitle)
                .message(emailMessage)
                .build();
    }
}
