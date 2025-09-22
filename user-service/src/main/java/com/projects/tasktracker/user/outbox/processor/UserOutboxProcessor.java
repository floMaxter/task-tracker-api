package com.projects.tasktracker.user.outbox.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.tasktracker.user.exception.OutboxSerializationException;
import com.projects.tasktracker.user.kafka.producer.CreatedUserProducer;
import com.projects.tasktracker.user.outbox.domain.OutboxRecord;
import com.projects.tasktracker.user.outbox.mapper.EmailEventMapper;
import com.projects.tasktracker.user.web.dto.response.CreateUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserOutboxProcessor implements OutboxRecordProcessor {

    private final ObjectMapper objectMapper;
    private final CreatedUserProducer createdUserProducer;
    private final EmailEventMapper emailEventMapper;

    @Override
    public void process(OutboxRecord outboxRecord) {
        try {
            var createUserResponse = objectMapper.readValue(outboxRecord.getPayload(), CreateUserResponse.class);
            var userWelcomeEmailEvent = emailEventMapper.toUserWelcomeEmailEvent(createUserResponse);
            createdUserProducer.sendCreateUserEvent(userWelcomeEmailEvent);
        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize outbox payload of type {}", outboxRecord.getType(), e);
            throw new OutboxSerializationException(e);
        }
    }
}
