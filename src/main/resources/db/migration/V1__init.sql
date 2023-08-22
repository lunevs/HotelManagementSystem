create table accounts (
                       id                    bigserial,
                       name              varchar(30) not null unique,
                       password              varchar(80) not null,
                       email                 varchar(50) unique,
                       primary key (id)
);

create table roles (
                       id                    serial,
                       name                  varchar(50) not null,
                       primary key (id)
);

CREATE TABLE accounts_roles (
                               account_id               bigint not null,
                             role_id               int not null,
                             primary key (account_id, role_id),
                             foreign key (account_id) references accounts (id),
                             foreign key (role_id) references roles (id)
);

select * from ACCOUNTS;


insert into roles (name)
values
    ('ROLE_USER'), ('ROLE_ADMIN');

insert into accounts (name, password, email)
values
    ('user', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'user@gmail.com'),
    ('admin', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'admin@gmail.com');

insert into accounts_roles (account_id, role_id)
values
    (1, 1),
    (2, 2);