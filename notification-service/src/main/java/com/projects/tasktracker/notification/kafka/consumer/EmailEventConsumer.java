package com.projects.tasktracker.notification.kafka.consumer;

import com.projects.tasktracker.notification.config.props.KafkaTopicProperties;
import com.projects.tasktracker.core.kafka.event.UserWelcomeEmailEvent;
import com.projects.tasktracker.notification.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@KafkaListener(topics = "#{kafkaTopicProperties.emailSendingTasks}")
public class EmailEventConsumer {

    private final MailService mailService;
    private final KafkaTopicProperties kafkaTopicProperties;

    @KafkaHandler
    public void consumeEmailEvent(UserWelcomeEmailEvent userWelcomeEmailEvent) {
        log.info("Consuming the message from email-sending-tasks-topic Topic: {}", userWelcomeEmailEvent);

        mailService.sendEmailMessage(
                userWelcomeEmailEvent.email(),
                userWelcomeEmailEvent.title(),
                userWelcomeEmailEvent.message()
        );

        log.info("Successfully processed message for {}", userWelcomeEmailEvent.email());
    }
}
