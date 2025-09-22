package com.projects.tasktracker.user.config.prop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "application.constraints")
@Getter
@Setter
public class ConstraintMessagesProperties {

    private Map<String, String> messages = new HashMap<>();
}
