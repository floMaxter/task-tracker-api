package com.projects.tasktracker.task.web.controller;

import com.projects.tasktracker.task.service.TaskService;
import com.projects.tasktracker.task.web.dto.request.CreateTaskRequest;
import com.projects.tasktracker.task.web.dto.response.CreateTaskResponse;
import com.projects.tasktracker.task.web.dto.response.TaskDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<CreateTaskResponse> createTask(@RequestHeader("X-User-Id") String userId,
                                                         @Valid @RequestBody CreateTaskRequest createTaskRequest) {
        var savedTask = taskService.createTask(Long.parseLong(userId), createTaskRequest);
        return ResponseEntity.ok(savedTask);
    }

    @GetMapping("/me")
    public ResponseEntity<List<TaskDto>> getUserTasks(@RequestHeader("X-User-Id") String userId) {
        var tasks = taskService.getTasks(Long.parseLong(userId));
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<TaskDto>> getUserTasksById(@PathVariable("id") Long userId) {
        var tasks = taskService.getTasks(userId);
        return ResponseEntity.ok(tasks);
    }
}
