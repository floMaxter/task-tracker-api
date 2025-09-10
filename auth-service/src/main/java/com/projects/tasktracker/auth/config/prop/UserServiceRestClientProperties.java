package com.projects.tasktracker.auth.config.prop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@ConfigurationProperties(prefix = "application.services.user-service")
@Getter
@Setter
public class UserServiceRestClientProperties {

    private String baseUri;
    private Duration connectionTimeout;
    private Duration readTimeout;
}
