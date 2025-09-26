package com.projects.tasktracker.user.outbox.processor;

import com.projects.tasktracker.user.outbox.domain.OutboxRecord;

public interface OutboxRecordProcessor {

    void process(OutboxRecord outboxRecord);
}
