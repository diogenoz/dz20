DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id                    bigserial,
  username              VARCHAR(30) NOT NULL UNIQUE,
  password              VARCHAR(80),
  email                 VARCHAR(50) UNIQUE,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS roles;
CREATE TABLE roles (
  id                    bigserial,
  name                  VARCHAR(50) NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS users_roles;
CREATE TABLE users_roles (
  user_id               BIGINT NOT NULL,
  role_id               BIGINT NOT NULL,
  PRIMARY KEY (user_id, role_id),
  FOREIGN KEY (user_id)
  REFERENCES users (id),
  FOREIGN KEY (role_id)
  REFERENCES roles (id)
);

INSERT INTO roles (name)
VALUES
('ROLE_USER'), ('ROLE_MANAGER'), ('ROLE_ADMIN');

INSERT INTO users (username, password, email)
VALUES
('admin', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'admin@gmail.com'),
('user', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'user@gmail.com');

INSERT INTO users_roles (user_id, role_id)
VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 1);

drop table if exists employees;
create table employees (id bigserial, name varchar(255), age int, primary key (id));
insert into employees (name, age) values ('Sidorov', 33), ('Ivanov', 21);

drop table if exists tasks;
create table tasks (id bigserial
, title varchar(255)
, owner_id bigint
, assignee_id bigint
, description varchar(255)
, status varchar(255)
, foreign key (owner_id) references employees (id)
, foreign key (assignee_id) references employees (id)
, primary key(id));

insert into tasks (title, owner_id, assignee_id, description, status) values
('Task#1', 1, 2, 'Task#1', 'Open'),
('Task#2', 2, 2, 'Task#2', 'Done'),
('Task#3', 2, 1, 'Task#3', 'InProgress'),
('Task#4', 1, 2, 'Task#4', 'Open'),
('Task#5', 1, 1, 'Task#5', 'Done');