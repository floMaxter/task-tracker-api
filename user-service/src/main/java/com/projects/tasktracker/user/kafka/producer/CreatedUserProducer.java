package com.projects.tasktracker.user.kafka.producer;

import com.projects.tasktracker.core.kafka.event.UserWelcomeEmailEvent;
import com.projects.tasktracker.user.config.prop.KafkaTopicProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class CreatedUserProducer {

    private final KafkaTopicProperties kafkaTopicProperties;
    private final KafkaTemplate<String, UserWelcomeEmailEvent> kafkaTemplate;

    public void sendCreateUserEvent(UserWelcomeEmailEvent emailMessage) {
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
