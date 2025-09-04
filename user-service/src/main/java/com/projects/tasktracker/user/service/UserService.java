package com.projects.tasktracker.user.service;

import com.projects.tasktracker.user.exception.UserNotFoundException;
import com.projects.tasktracker.user.repository.UserRepository;
import com.projects.tasktracker.user.web.dto.request.CreateUserRequest;
import com.projects.tasktracker.user.web.dto.response.UserResponse;
import com.projects.tasktracker.user.web.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserResponse findById(Long id) {
        var findUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.toDto(findUser);
    }

    public void createUser(CreateUserRequest createUserRequest) {
        var createdUser = userMapper.fromCreateDto(createUserRequest);
        createdUser.setPassword(passwordEncoder.encode(createdUser.getPassword()));
        userRepository.save(createdUser);
    }
}
