package com.projects.tasktracker.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.tasktracker.user.outbox.domain.OutboxType;
import com.projects.tasktracker.user.exception.OutboxSerializationException;
import com.projects.tasktracker.user.exception.UserNotFoundException;
import com.projects.tasktracker.user.outbox.service.OutboxUserService;
import com.projects.tasktracker.user.repository.UserRepository;
import com.projects.tasktracker.user.web.dto.request.CreateUserRequest;
import com.projects.tasktracker.user.web.dto.response.CreateUserResponse;
import com.projects.tasktracker.user.web.dto.response.UserCredentialsResponse;
import com.projects.tasktracker.user.web.dto.response.UserResponse;
import com.projects.tasktracker.user.web.dto.response.UserSummaryResponse;
import com.projects.tasktracker.user.web.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final OutboxUserService outboxUserService;
    private final ObjectMapper objectMapper;

    public UserResponse findById(Long id) {
        var findUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.toUserResponse(findUser);
    }

    public List<UserSummaryResponse> findAllSummaries() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserSummaryResponse)
                .toList();
    }

    public UserCredentialsResponse getUserCredentialsByEmail(String email) {
        var findUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        return userMapper.toUserCredentialsResponse(findUser);
    }

    public UserSummaryResponse getUserSummaryByEmail(String email) {
        var findUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        return userMapper.toUserSummaryResponse(findUser);
    }

    @Transactional
    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
        var createdUser = userMapper.fromCreateDto(createUserRequest);
        createdUser.setPassword(passwordEncoder.encode(createdUser.getPassword()));

        var savedUser = userRepository.save(createdUser);

        var createdUserResponse = userMapper.toCreateUserResponse(savedUser);
        try {
            outboxUserService.create(objectMapper.writeValueAsString(createdUserResponse), OutboxType.USER_CREATED_EVENT);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize outbox payload of type {}", OutboxType.USER_CREATED_EVENT, e);
            throw new OutboxSerializationException(e);
        }

        return createdUserResponse;
    }
}
