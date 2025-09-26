package com.projects.tasktracker.scheduler.config.prop;

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
