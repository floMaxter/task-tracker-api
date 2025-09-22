package com.projects.tasktracker.user.outbox.repository;

import com.projects.tasktracker.user.outbox.domain.OutboxRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutboxRecordRepository extends JpaRepository<OutboxRecord, Long> {
}
