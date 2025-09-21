package com.projects.tasktracker.auth.kafka.producer;

import com.projects.tasktracker.auth.config.prop.KafkaTopicProperties;
import com.projects.tasktracker.core.kafka.event.UserWelcomeEmailEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthEventProducer {

    private final KafkaTopicProperties kafkaTopicProperties;
    private final KafkaTemplate<String, UserWelcomeEmailEvent> kafkaTemplate;

    public void sendUserRegisteredEvent(UserWelcomeEmailEvent emailMessage) {
        log.info("Sending user registered event email={}", emailMessage.email());

        var message = MessageBuilder
                .withPayload(emailMessage)
                .setHeader(KafkaHeaders.TOPIC, kafkaTopicProperties.getEmailSendingTasks())
                .build();
        kafkaTemplate.send(message);

        log.info("Send user registered event email={}", emailMessage.email());
    }
}
