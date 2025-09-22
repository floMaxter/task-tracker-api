package com.projects.tasktracker.notification.kafka.consumer;

import com.projects.tasktracker.core.kafka.event.UserWelcomeEmailEvent;
import com.projects.tasktracker.notification.config.props.KafkaTopicProperties;
import com.projects.tasktracker.notification.domain.ProcessedEvent;
import com.projects.tasktracker.notification.exeption.NonRetryableException;
import com.projects.tasktracker.notification.repository.ProcessedEventRepository;
import com.projects.tasktracker.notification.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@KafkaListener(topics = "#{kafkaTopicProperties.emailSendingTasks}")
public class EmailEventConsumer {

    private final MailService mailService;
    private final ProcessedEventRepository processedEventRepository;
    private final KafkaTopicProperties kafkaTopicProperties;

    @Transactional
    @KafkaHandler
    public void consumeEmailEvent(@Payload UserWelcomeEmailEvent userWelcomeEmailEvent,
                                  @Header("messageId") String messageId) {
        log.info("Consuming the message from email-sending-tasks-topic Topic: {}", userWelcomeEmailEvent);

        var maybeProcessedEvent = processedEventRepository.findByMessageId(messageId);
        if (maybeProcessedEvent.isPresent()) {
            log.info("Duplicate message id: {}", messageId);
            return;
        }

        mailService.sendEmailMessage(
                userWelcomeEmailEvent.email(),
                userWelcomeEmailEvent.title(),
                userWelcomeEmailEvent.message()
        );

        try {
            processedEventRepository.save(new ProcessedEvent(messageId, userWelcomeEmailEvent.email()));
        } catch (DataIntegrityViolationException ex) {
            log.error("Exception during saving processed event: {}", ex.getMessage());
            throw new NonRetryableException(ex);
        }

        log.info("Successfully processed message for {}", userWelcomeEmailEvent.email());
    }
}
