package com.projects.tasktracker.user.web.mapper;

import com.projects.tasktracker.user.domain.User;
import com.projects.tasktracker.user.web.dto.request.CreateUserRequest;
import com.projects.tasktracker.user.web.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User fromCreateDto(CreateUserRequest dto);

    UserResponse toDto(User user);
}
