package com.projects.tasktracker.scheduler.client.task;

import com.projects.tasktracker.scheduler.client.task.dto.TaskDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "task-service")
public interface TaskServiceClient {

    @GetMapping("/api/v1/tasks/user/{id}")
    List<TaskDto> getAll(@PathVariable("id") Long userId);
}
