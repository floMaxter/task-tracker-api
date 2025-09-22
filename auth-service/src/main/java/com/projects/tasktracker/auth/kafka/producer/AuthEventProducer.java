package com.projects.tasktracker.auth.kafka.producer;

import com.projects.tasktracker.auth.config.prop.KafkaTopicProperties;
import com.projects.tasktracker.core.kafka.event.UserWelcomeEmailEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthEventProducer {

    private final KafkaTopicProperties kafkaTopicProperties;
    private final KafkaTemplate<String, UserWelcomeEmailEvent> kafkaTemplate;

    public void sendUserRegisteredEvent(UserWelcomeEmailEvent emailMessage) {
        log.info("Sending user registered event email={}", emailMessage.email());

        var producerRecord = new ProducerRecord<>(
                kafkaTopicProperties.getEmailSendingTasks(),
                emailMessage.email(),
                emailMessage
        );
        producerRecord.headers().add("messageId", UUID.randomUUID().toString().getBytes());

        kafkaTemplate.send(producerRecord);

        log.info("Send user registered event email={}", emailMessage.email());
    }
}
