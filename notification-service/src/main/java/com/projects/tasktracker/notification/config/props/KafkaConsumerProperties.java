package com.projects.tasktracker.notification.config.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.kafka.consumer")
public class KafkaConsumerProperties {

    private String bootstrapServers;
    private String groupId;
    private String autoOffsetReset;
    private Map<String, String> properties = new HashMap<>();

    public String getSpringJsonTrustedPackages() {
        return properties.get("spring.json.trusted.packages");
    }
}
