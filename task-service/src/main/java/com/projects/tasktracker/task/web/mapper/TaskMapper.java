package com.projects.tasktracker.task.web.mapper;

import com.projects.tasktracker.task.domain.Task;
import com.projects.tasktracker.task.web.dto.request.CreateTaskRequest;
import com.projects.tasktracker.task.web.dto.response.CreateTaskResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ownerId", ignore = true)
    @Mapping(target = "status", constant = "TODO")
    Task fromCreateRequest(CreateTaskRequest dto);

    CreateTaskResponse toCreateTaskResponse(Task entity);
}
