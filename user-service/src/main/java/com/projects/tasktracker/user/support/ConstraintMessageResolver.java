package com.projects.tasktracker.user.support;

import com.projects.tasktracker.user.config.prop.ConstraintMessagesProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ConstraintMessageResolver {

    private final ConstraintMessagesProperties props;

    public String resolveMessage(String dbMessage) {
        return props.getMessages().entrySet().stream()
                .filter(entry -> dbMessage.contains(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse("Duplicate value");
    }
}
