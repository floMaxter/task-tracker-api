package com.projects.tasktracker.scheduler.kafka;

import com.projects.tasktracker.core.kafka.event.EmailSendingTask;
import com.projects.tasktracker.scheduler.config.prop.KafkaTopicProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class EmailSendingTaskProducer {

    private final KafkaTopicProperties kafkaTopicProperties;
    private final KafkaTemplate<String, EmailSendingTask> kafkaTemplate;

    public void sendCreateUserEvent(EmailSendingTask emailSendingTask) {
        log.info("Sending task analytics to the email={}", emailSendingTask.email());

        var producerRecord = new ProducerRecord<>(
                kafkaTopicProperties.getEmailSendingTasks(),
                emailSendingTask.email(),
                emailSendingTask
        );
        producerRecord.headers().add("messageId", UUID.randomUUID().toString().getBytes());

        kafkaTemplate.send(producerRecord);

        log.info("Send task analytics to the email={}", emailSendingTask.email());
    }
}
