package com.projects.tasktracker.gateway.config.prop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "application.auth")
public class SecurityPathsProperties {

    private List<String> whitelist = new ArrayList<>();
}
