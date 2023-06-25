create table if not exists users
(
    id       bigint       not null auto_increment,
    name     varchar(255) not null,
    password varchar(255) not null,
    primary key (id),
    unique (name)
);

create table if not exists paths
(
    id          bigint       not null auto_increment,
    bus_id     bigint       not null,
    get_on_bus_stop varchar(255) not null,
    get_off_bus_stop varchar(255) not null,
    fare        bigint not null ,
    travel_time bigint not null,
    primary key (id),
    unique (get_on_bus_stop, get_off_bus_stop, bus_id)
);

create table if not exists buses
(
    id          bigint       not null auto_increment,
    bus_number  varchar(255) not null,
    capacity    bigint not null,
    departure_time varchar(20) not null,
    primary key (id),
    unique (bus_number)
);

create table if not exists bus_bus_stops
(
    id          bigint       not null auto_increment,
    bus_id     bigint       not null,
    bus_stop_name varchar(255) not null,
    arrival_time varchar(20) not null,
    forecasted_demand bigint not null,
    unique (bus_id, bus_stop_name)
);