package com.projects.tasktracker.scheduler.config.prop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.kafka.producer")
public class KafkaProducerProperties {

    private String bootstrapServers;
    private String keySerializer;
    private String valueSerializer;
    private String acks;
    private Map<String, String> properties = new HashMap<>();

    public String getDeliveryTimeout() {
        return properties.get("delivery.timeout.ms");
    }

    public String getRequestTimeout() {
        return properties.get("request.timeout.ms");
    }

    public String getLinger() {
        return properties.get("linger.ms");
    }

    public String getEnableIdempotence() {
        return properties.get("enable.idempotence");
    }

    public String getMaxInFlightRequestsPerConnection() {
        return properties.get("max.in.flight.requests.per.connection");
    }
}
