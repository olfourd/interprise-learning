CREATE DATABASE users
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

# todo: не работает из docker-compose. перекинуть в инит скрипт в апп ресурсы
CREATE TABLE role
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE
);

INSERT INTO role(name)
VALUES ('ROLE_ADMIN'),
       ('ROLE_CLIENT'),
       ('ROLE_CARRIER'),
       ('ROLE_PRODUCER');

CREATE TABLE app_user
(
    id       SERIAL PRIMARY KEY,
    email    VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(500) NOT NULL
);

CREATE TABLE user_role
(
    user_id SERIAL NOT NULL,
    role_id SERIAL NOT NULL,

    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES app_user (id),
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES role (id)
)
