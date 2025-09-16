package com.projects.tasktracker.task.service;

import com.projects.tasktracker.task.repository.TaskRepository;
import com.projects.tasktracker.task.web.dto.request.CreateTaskRequest;
import com.projects.tasktracker.task.web.dto.response.CreateTaskResponse;
import com.projects.tasktracker.task.web.mapper.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public CreateTaskResponse createTask(Long userId, CreateTaskRequest createTaskRequest) {
        var newTask = taskMapper.fromCreateRequest(createTaskRequest);
        newTask.setOwnerId(userId);
        var savedTask = taskRepository.save(newTask);

        return taskMapper.toCreateTaskResponse(savedTask);
    }
}
