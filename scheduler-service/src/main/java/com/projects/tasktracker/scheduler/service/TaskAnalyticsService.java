package com.projects.tasktracker.scheduler.service;

import com.projects.tasktracker.scheduler.client.task.TaskServiceClient;
import com.projects.tasktracker.scheduler.client.user.UserServiceClient;
import com.projects.tasktracker.scheduler.kafka.EmailSendingTaskProducer;
import com.projects.tasktracker.scheduler.mapper.UserTasksSummaryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskAnalyticsService {

    private final UserServiceClient userServiceClient;
    private final TaskServiceClient taskServiceClient;
    private final TaskReportService taskReportService;
    private final UserTasksSummaryMapper userTasksSummaryMapper;
    private final EmailSendingTaskProducer emailSendingTaskProducer;

    public void processTasksForAllUsers() {
        var userSummaries = userServiceClient.getAllUserSummaries();
        for (var userSummary : userSummaries) {
            var userTasks = taskServiceClient.getAll(userSummary.id());
            var userTasksSummary = userTasksSummaryMapper.toUserTasksSummary(userSummary, userTasks);

            taskReportService.buildEmailSendingTask(userTasksSummary)
                    .ifPresent(emailSendingTaskProducer::sendCreateUserEvent);
        }
    }
}
