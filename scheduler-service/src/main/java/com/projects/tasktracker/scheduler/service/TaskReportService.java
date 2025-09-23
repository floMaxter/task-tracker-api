package com.projects.tasktracker.scheduler.service;

import com.projects.tasktracker.core.kafka.event.EmailSendingTask;
import com.projects.tasktracker.scheduler.dto.UserTasksSummary;
import com.projects.tasktracker.scheduler.strategy.TaskReportStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskReportService {

    private final List<TaskReportStrategy> taskReportStrategies;

    public Optional<EmailSendingTask> buildEmailSendingTask(UserTasksSummary userTasksSummary) {
        return taskReportStrategies.stream()
                .filter(s -> s.isApplicable(userTasksSummary))
                .findFirst()
                .map(s -> s.buildEmailSendingTask(userTasksSummary));
    }
}
