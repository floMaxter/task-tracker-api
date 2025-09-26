package com.projects.tasktracker.gateway.config.prop;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application.services.auth-service")
@Getter
@Setter
public class AuthServiceRestClientProperties {

    private String baseUri;
}
