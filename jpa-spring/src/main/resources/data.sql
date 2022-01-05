create schema learning;

create table User
(
    id          int         not null auto_increment,
    name        varchar(20) not null,
    position_id varchar(20) not null
);

create table Position
(
    id                 int         not null auto_increment,
    name               varchar(20) not null,
    description        varchar(60) not null,

    created_by         varchar(20),
    created_date       timestamp,
    last_modified_by   varchar(20),
    last_modified_date timestamp,
    version            int
)