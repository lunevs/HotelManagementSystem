

insert into roles (name) values ('ROLE_ADMIN');

insert into accounts (name, password, email, deleted, role_id)
values ('admin', '{bcrypt}$2a$12$ZJxPYIUg0al2/qf0AwqI1./QfF.WAVrYXt/GcRDMJB2CKFcSx9ksO', 'admin@gmail.com', false, 1);


insert into roles (name) values ('ROLE_USER');

insert into accounts (name, password, email, deleted, role_id)
values ('user', '{bcrypt}$2a$12$ZJxPYIUg0al2/qf0AwqI1./QfF.WAVrYXt/GcRDMJB2CKFcSx9ksO', 'user@gmail.com', false, 2);

insert into roles (name) values ('ROLE_MODERATOR');
