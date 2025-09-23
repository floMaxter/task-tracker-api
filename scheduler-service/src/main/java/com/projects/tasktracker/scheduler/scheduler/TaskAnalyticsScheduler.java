package com.projects.tasktracker.scheduler.scheduler;

import com.projects.tasktracker.scheduler.service.TaskAnalyticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskAnalyticsScheduler {

    private final TaskAnalyticsService taskAnalyticsService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void runDailyTaskReport() {
        log.info("Task analytics scheduler started");
        taskAnalyticsService.processTasksForAllUsers();
        log.info("Task analytics scheduler finished");
    }
}
