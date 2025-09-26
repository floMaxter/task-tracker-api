package com.projects.tasktracker.scheduler.strategy;

import com.projects.tasktracker.core.kafka.event.EmailSendingTask;
import com.projects.tasktracker.scheduler.dto.UserTasksSummary;

public interface TaskReportStrategy {

    boolean isApplicable(UserTasksSummary userTasksSummary);

    EmailSendingTask buildEmailSendingTask(UserTasksSummary userTasksSummary);
}
