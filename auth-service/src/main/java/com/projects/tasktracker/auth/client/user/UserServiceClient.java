package com.projects.tasktracker.auth.client.user;

import com.projects.tasktracker.auth.client.user.dto.request.CreateUserRequest;
import com.projects.tasktracker.auth.client.user.dto.response.CreateUserResponse;
import com.projects.tasktracker.auth.client.user.dto.response.UserCredentialsResponse;
import com.projects.tasktracker.auth.client.user.dto.response.UserSummaryResponse;
import com.projects.tasktracker.auth.exception.UserServiceClientException;
import com.projects.tasktracker.auth.exception.UserServiceServerException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class UserServiceClient {

    private final RestClient userServiceRestClient;

    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
        return userServiceRestClient.post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(createUserRequest)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, resp) -> {
                    throw new UserServiceClientException(String.format("4xx from user-service when trying to create user" +
                            "statusCode=%s, statusText=%s", resp.getStatusCode(), resp.getStatusText()
                    ));
                })
                .onStatus(HttpStatusCode::is5xxServerError, (req, resp) -> {
                    throw new UserServiceServerException(String.format("5xx from user-service when trying to create user" +
                            "statusCode=%s, statusText=%s", resp.getStatusCode(), resp.getStatusText()
                    ));
                })
                .toEntity(CreateUserResponse.class)
                .getBody();
    }

    public UserCredentialsResponse getUserCredentialsByEmail(String email) {
        return userServiceRestClient.get()
                .uri("/api/v1/users/by-email/" + email + "/credentials")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, resp) -> {
                    throw new UserServiceClientException(String.format("4xx from user-service when trying to create user" +
                            "statusCode=%s, statusText=%s", resp.getStatusCode(), resp.getStatusText()
                    ));
                })
                .onStatus(HttpStatusCode::is5xxServerError, (req, resp) -> {
                    throw new UserServiceServerException(String.format("5xx from user-service when trying to create user" +
                            "statusCode=%s, statusText=%s", resp.getStatusCode(), resp.getStatusText()
                    ));
                })
                .toEntity(UserCredentialsResponse.class)
                .getBody();
    }

    public UserSummaryResponse getUserSummaryByEmail(String email) {
        return userServiceRestClient.get()
                .uri("/api/v1/users/by-email/" + email + "/summary")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, resp) -> {
                    throw new UserServiceClientException(String.format("4xx from user-service when trying to create user" +
                            "statusCode=%s, statusText=%s", resp.getStatusCode(), resp.getStatusText()
                    ));
                })
                .onStatus(HttpStatusCode::is5xxServerError, (req, resp) -> {
                    throw new UserServiceServerException(String.format("5xx from user-service when trying to create user" +
                            "statusCode=%s, statusText=%s", resp.getStatusCode(), resp.getStatusText()
                    ));
                })
                .toEntity(UserSummaryResponse.class)
                .getBody();
    }
}
