package com.projects.tasktracker.user.outbox.mapper;

import com.projects.tasktracker.core.kafka.event.EmailSendingTask;
import com.projects.tasktracker.user.web.dto.response.CreateUserResponse;
import org.springframework.stereotype.Component;

@Component
public class EmailSendingTaskMapper {

    public EmailSendingTask toEmailSendingTask(CreateUserResponse createUserResponse) {
        return EmailSendingTask.builder()
                .email(createUserResponse.email())
                .title("Task tracker greeting")
                .message(String.format("Hello, %s! Thank you for choosing to use our task tracker.", createUserResponse.username()))
                .build();
    }
}
