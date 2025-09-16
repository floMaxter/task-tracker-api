package com.projects.tasktracker.task.web.mapper;

import com.projects.tasktracker.task.domain.Task;
import com.projects.tasktracker.task.web.dto.request.CreateTaskRequest;
import com.projects.tasktracker.task.web.dto.response.CreateTaskResponse;
import com.projects.tasktracker.task.web.dto.response.TaskDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ownerId", ignore = true)
    @Mapping(target = "status", constant = "TODO")
    Task fromCreateRequest(CreateTaskRequest dto);

    CreateTaskResponse toCreateTaskResponse(Task entity);

    List<TaskDto> toDtos(List<Task> entities);
}
