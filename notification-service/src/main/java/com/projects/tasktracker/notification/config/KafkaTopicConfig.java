package com.projects.tasktracker.notification.config;

import com.projects.tasktracker.notification.config.props.KafkaTopicProperties;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@RequiredArgsConstructor
public class KafkaTopicConfig {

    private final KafkaTopicProperties kafkaTopicProperties;

    @Bean
    public NewTopic emailSendingTopic() {
        return TopicBuilder
                .name(kafkaTopicProperties.getEmailSendingTasks())
                .build();
    }
}
