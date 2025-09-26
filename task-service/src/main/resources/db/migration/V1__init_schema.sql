create schema if not exists task_tracker;

create table task_tracker.tasks
(
    id           serial primary key,
    title        varchar     not null,
    description  text,
    owner_id     bigint      not null,
    status       varchar(50) not null,
    completed_at timestamp,
    created_at   timestamp   not null default now(),
    updated_at   timestamp   not null default now()
)