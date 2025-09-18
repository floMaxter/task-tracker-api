package com.projects.tasktracker.auth.config.prop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application.kafka.topics")
@Getter
@Setter
public class KafkaTopicProperties {

    String emailSendingTasks;
}
