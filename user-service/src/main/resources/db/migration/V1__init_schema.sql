create schema if not exists task_tracker;

create table task_tracker.users
(
    id       serial primary key,
    username varchar not null check (length(trim(username)) > 0),
    email    varchar not null check (length(trim(username)) > 0),
    password varchar
);
create unique index idx_users_email on task_tracker.users (email);