package com.projects.tasktracker.task.service;

import com.projects.tasktracker.task.repository.TaskRepository;
import com.projects.tasktracker.task.web.dto.request.CreateTaskRequest;
import com.projects.tasktracker.task.web.dto.response.CreateTaskResponse;
import com.projects.tasktracker.task.web.dto.response.TaskDto;
import com.projects.tasktracker.task.web.mapper.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public CreateTaskResponse createTask(Long ownerId, CreateTaskRequest createTaskRequest) {
        var newTask = taskMapper.fromCreateRequest(createTaskRequest);
        newTask.setOwnerId(ownerId);
        var savedTask = taskRepository.save(newTask);

        return taskMapper.toCreateTaskResponse(savedTask);
    }

    public List<TaskDto> getTasks(Long ownerId) {
        var findTasks = taskRepository.findByOwnerId(ownerId);
        return taskMapper.toDtos(findTasks);
    }
}
