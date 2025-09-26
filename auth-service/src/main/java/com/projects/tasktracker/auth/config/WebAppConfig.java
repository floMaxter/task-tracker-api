package com.projects.tasktracker.auth.config;

import com.projects.tasktracker.auth.config.prop.UserServiceRestClientProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class WebAppConfig {

    private final UserServiceRestClientProperties userServiceRestClientProperties;

    @Bean
    @LoadBalanced
    public RestClient.Builder loadBalancerRestClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    public RestClient userServiceRestClient(RestClient.Builder builder) {
        return builder
                .baseUrl(userServiceRestClientProperties.getBaseUri())
                .requestFactory(clientHttpRequestFactory())
                .build();
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() {
        var factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(userServiceRestClientProperties.getConnectionTimeout());
        factory.setReadTimeout(userServiceRestClientProperties.getReadTimeout());
        return factory;
    }
}
