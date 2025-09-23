package com.projects.tasktracker.user.web.controller;

import com.projects.tasktracker.user.service.UserService;
import com.projects.tasktracker.user.web.dto.request.CreateUserRequest;
import com.projects.tasktracker.user.web.dto.response.CreateUserResponse;
import com.projects.tasktracker.user.web.dto.response.UserCredentialsResponse;
import com.projects.tasktracker.user.web.dto.response.UserResponse;
import com.projects.tasktracker.user.web.dto.response.UserSummaryResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public CreateUserResponse createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        return userService.createUser(createUserRequest);
    }

    @GetMapping("/summary")
    public List<UserSummaryResponse> getAllUserSummaries() {
        return userService.findAllSummaries();
    }

    @GetMapping("/{user-id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse findUserById(@PathVariable("user-id")
                                     @Positive(message = "Id must be positive") Long userId) {
        return userService.findById(userId);
    }

    @GetMapping("/by-email/{email}/credentials")
    public UserCredentialsResponse getUserCredentialsByEmail(@PathVariable("email") String email) {
        return userService.getUserCredentialsByEmail(email);
    }

    @GetMapping("/by-email/{email}/summary")
    public UserSummaryResponse getUserSummaryByEmail(@PathVariable("email") String email) {
        return userService.getUserSummaryByEmail(email);
    }
}
