package com.projects.tasktracker.scheduler.client.user;

import com.projects.tasktracker.scheduler.client.user.dto.UserSummaryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "user-service")
public interface UserServiceClient {

    @GetMapping("/api/v1/users/summary")
    List<UserSummaryResponse> getAllUserSummaries();
}
