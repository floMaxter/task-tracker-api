package com.projects.tasktracker.user.outbox.scheduler;

import com.projects.tasktracker.user.outbox.service.OutboxUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class OutboxRecordScheduler {

    private final OutboxUserService outboxUserService;

    @Scheduled(fixedDelay = 10_000)
    public void run() {
        log.info("Outbox user processing started");
        outboxUserService.processAll();
        log.info("Outbox user processing finished");
    }
}
