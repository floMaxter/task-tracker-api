package com.projects.tasktracker.scheduler.strategy;

import com.projects.tasktracker.core.kafka.event.EmailSendingTask;
import com.projects.tasktracker.scheduler.client.task.dto.TaskDto;
import com.projects.tasktracker.scheduler.dto.UserTasksSummary;
import com.projects.tasktracker.scheduler.util.TaskFilterUtils;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CompleteTasksStrategy implements TaskReportStrategy {

    @Override
    public boolean isApplicable(UserTasksSummary userTasksSummary) {
        return !userTasksSummary.completedTask().isEmpty()
                & userTasksSummary.incompletedTask().isEmpty();
    }

    @Override
    public EmailSendingTask buildEmailSendingTask(UserTasksSummary userTasksSummary) {
        var tasksCompletedToday = TaskFilterUtils.filterTasksCompletedInLast24h(userTasksSummary.completedTask());
        var countCompletedTaskToday = tasksCompletedToday.size();
        var emailTitle = String.format("You have completed %d tasks today", countCompletedTaskToday);

        var taskTitles = tasksCompletedToday.stream()
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
