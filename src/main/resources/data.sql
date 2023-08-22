

insert into roles (name) values ('ROLE_ADMIN');

insert into accounts (name, password, email, deleted)
values ('admin', '{bcrypt}$2a$12$ZJxPYIUg0al2/qf0AwqI1./QfF.WAVrYXt/GcRDMJB2CKFcSx9ksO', 'admin@gmail.com', false);

insert into accounts_roles (account_id, role_id) values (1, 1);


insert into roles (name) values ('ROLE_USER');

insert into accounts (name, password, email, deleted)
values ('user', '{bcrypt}$2a$12$ZJxPYIUg0al2/qf0AwqI1./QfF.WAVrYXt/GcRDMJB2CKFcSx9ksO', 'user@gmail.com', false);

insert into accounts_roles (account_id, role_id) values (2, 2);