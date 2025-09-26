package com.projects.tasktracker.gateway.config;

import com.projects.tasktracker.gateway.config.prop.AuthServiceRestClientProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebAppConfig {

    private final AuthServiceRestClientProperties userServiceRestClientProperties;

    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient authServiceWebClient(@LoadBalanced WebClient.Builder builder) {
        return builder
                .baseUrl(userServiceRestClientProperties.getBaseUri())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
