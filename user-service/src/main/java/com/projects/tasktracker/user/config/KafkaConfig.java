package com.projects.tasktracker.user.config;


import com.projects.tasktracker.core.kafka.event.UserWelcomeEmailEvent;
import com.projects.tasktracker.user.config.prop.KafkaProducerProperties;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    private final KafkaProducerProperties producerProperties;

    public Map<String, Object> producerConfig() {
        var config = new HashMap<String, Object>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, producerProperties.getBootstrapServers());
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, producerProperties.getKeySerializer());
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, producerProperties.getValueSerializer());
        config.put(ProducerConfig.ACKS_CONFIG, producerProperties.getAcks());
        config.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, producerProperties.getDeliveryTimeout());
        config.put(ProducerConfig.LINGER_MS_CONFIG, producerProperties.getLinger());
        config.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, producerProperties.getRequestTimeout());
        config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, producerProperties.getEnableIdempotence());
        config.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, producerProperties.getMaxInFlightRequestsPerConnection());

        return config;
    }

    @Bean
    public ProducerFactory<String, UserWelcomeEmailEvent> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, UserWelcomeEmailEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
