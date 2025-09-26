package com.projects.tasktracker.gateway.security.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AuthHeaderUtil {

    public String BEARER_SCHEMA = "Bearer ";

    public String bearer(String token) {
        return BEARER_SCHEMA + token;
    }
}
