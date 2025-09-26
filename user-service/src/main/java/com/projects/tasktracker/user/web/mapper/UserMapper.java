package com.projects.tasktracker.user.web.mapper;

import com.projects.tasktracker.user.domain.User;
import com.projects.tasktracker.user.web.dto.request.CreateUserRequest;
import com.projects.tasktracker.user.web.dto.response.CreateUserResponse;
import com.projects.tasktracker.user.web.dto.response.UserCredentialsResponse;
import com.projects.tasktracker.user.web.dto.response.UserResponse;
import com.projects.tasktracker.user.web.dto.response.UserSummaryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User fromCreateDto(CreateUserRequest dto);

    UserResponse toUserResponse(User user);

    CreateUserResponse toCreateUserResponse(User user);

    UserCredentialsResponse toUserCredentialsResponse(User user);

    UserSummaryResponse toUserSummaryResponse(User user);
}
