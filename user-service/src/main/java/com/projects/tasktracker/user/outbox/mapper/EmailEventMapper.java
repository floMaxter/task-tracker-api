package com.projects.tasktracker.user.outbox.mapper;

import com.projects.tasktracker.core.kafka.event.UserWelcomeEmailEvent;
import com.projects.tasktracker.user.web.dto.response.CreateUserResponse;
import org.springframework.stereotype.Component;

@Component
public class EmailEventMapper {

    public UserWelcomeEmailEvent toUserWelcomeEmailEvent(CreateUserResponse createUserResponse) {
        return UserWelcomeEmailEvent.builder()
                .email(createUserResponse.email())
                .title("Task tracker greeting")
                .message(String.format("Hello, %s! Thank you for choosing to use our task tracker.", createUserResponse.username()))
                .build();
    }
}
