create table task_tracker.outbox_records
(
    id         serial primary key,
    payload    varchar   not null,
    created_at timestamp not null default now(),
    type       varchar
)