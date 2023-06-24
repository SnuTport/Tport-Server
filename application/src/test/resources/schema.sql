create table if not exists users
(
    id       bigint       not null auto_increment,
    name     varchar(255) not null,
    password varchar(255) not null,
    primary key (id)
)