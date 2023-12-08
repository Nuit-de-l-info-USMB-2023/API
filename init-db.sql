\c api

create table roles
(
    id bigserial primary key,
    name varchar(10) not null
);
alter table roles owner to postgres;

insert into roles(id, name) values (1, 'USER'), (2, 'ADMIN');

create table users
(
    id bigserial primary key,
    password varchar(120) not null,
    username varchar(20) not null unique,
    role_id bigint not null references roles(id)
);
alter table users owner to postgres;

create table categories
(
    id bigserial primary key,
    name varchar(20) not null unique
);

alter table categories owner to postgres;

create table questions
(
    id bigserial primary key,
    content varchar(100) not null,
    category_id bigint not null references categories(id)
);

alter table questions owner to postgres;

create table answers
(
    id bigserial primary key,
    content varchar(100) not null,
    content_answer varchar(300) not null,
    is_good_answer boolean not null,
    question_id bigint not null references questions(id)
);

alter table answers owner to postgres;
