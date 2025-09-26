create schema if not exists task_tracker;

create table task_tracker.notifications
(
    id              serial primary key,
    message_id      varchar not null,
    recipient_email varchar not null
);
create unique index idx_notifications_message_id on task_tracker.notifications (message_id);