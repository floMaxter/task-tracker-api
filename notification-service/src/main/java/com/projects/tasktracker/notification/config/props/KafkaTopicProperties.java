package com.projects.tasktracker.notification.config.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "application.kafka.topics")
public class KafkaTopicProperties {

    String emailSendingTasks;
}


