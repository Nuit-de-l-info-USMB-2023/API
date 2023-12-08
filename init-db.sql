\c api

create table roles
(
    id bigserial primary key,
    name varchar(10) not null
);
alter table roles owner to postgres;

create table users
(
    id bigserial primary key,
    password varchar(120) not null,
    username varchar(20) not null unique,
    role_id bigint not null references roles(id)
);
alter table users owner to postgres;
insert into roles(id, name) values (1, 'USER'), (2, 'ADMIN');
