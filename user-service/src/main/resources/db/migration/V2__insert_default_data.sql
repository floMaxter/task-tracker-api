insert into task_tracker.users(id, username, email, password)
values (1, 'Default user 1', 'default_1@gmail.com', '$2a$12$Z858/nNOA6GCIKjfLUSVduvzNxdtDT6bZCtMc5z/tNrsK.dQ21N9S'),
       (2, 'Default user 2', 'default_2@gmail.com', '$2a$12$Z858/nNOA6GCIKjfLUSVduvzNxdtDT6bZCtMc5z/tNrsK.dQ21N9S');

select setval('task_tracker.users_id_seq', (select max(id) from task_tracker.users));