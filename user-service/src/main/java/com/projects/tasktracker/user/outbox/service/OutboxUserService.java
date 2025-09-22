package com.projects.tasktracker.user.outbox.service;

import com.projects.tasktracker.user.outbox.domain.OutboxRecord;
import com.projects.tasktracker.user.outbox.domain.OutboxType;
import com.projects.tasktracker.user.outbox.processor.OutboxRecordProcessor;
import com.projects.tasktracker.user.outbox.repository.OutboxRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxUserService {

    private final OutboxRecordRepository outboxRecordRepository;
    private final OutboxRecordProcessor recordProcessor;

    public void create(String payload, OutboxType outboxType) {
        var outboxRecord = new OutboxRecord();
        outboxRecord.setPayload(payload);
        outboxRecord.setType(outboxType);
        outboxRecordRepository.save(outboxRecord);
    }

    public void processAll() {
        outboxRecordRepository.findAll()
                .forEach(this::process);
    }

    private void process(OutboxRecord outboxRecord) {
        try {
            recordProcessor.process(outboxRecord);
            outboxRecordRepository.delete(outboxRecord);
            log.info("Processed data: {}", outboxRecord.getPayload());
        } catch (Exception ex) {
            log.warn("Failed to process data", ex);
        }

    }
}
