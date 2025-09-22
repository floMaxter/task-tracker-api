package com.projects.tasktracker.user.outbox.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.tasktracker.user.exception.OutboxSerializationException;
import com.projects.tasktracker.user.kafka.producer.EmailSendingTaskProducer;
import com.projects.tasktracker.user.outbox.domain.OutboxRecord;
import com.projects.tasktracker.user.outbox.mapper.EmailSendingTaskMapper;
import com.projects.tasktracker.user.web.dto.response.CreateUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserOutboxProcessor implements OutboxRecordProcessor {

    private final ObjectMapper objectMapper;
    private final EmailSendingTaskProducer emailSendingTaskProducer;
    private final EmailSendingTaskMapper emailSendingTaskMapper;

    @Override
    public void process(OutboxRecord outboxRecord) {
        try {
            var createUserResponse = objectMapper.readValue(outboxRecord.getPayload(), CreateUserResponse.class);
            var userWelcomeEmailEvent = emailSendingTaskMapper.toEmailSendingTask(createUserResponse);
            emailSendingTaskProducer.sendCreateUserEvent(userWelcomeEmailEvent);
        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize outbox payload of type {}", outboxRecord.getType(), e);
            throw new OutboxSerializationException(e);
        }
    }
}
