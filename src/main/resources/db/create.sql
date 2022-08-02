CREATE DATABASE organization_api;
CREATE TABLE departments (id serial PRIMARY KEY, name VARCHAR UNIQUE, description VARCHAR, created BIGINT, updated BIGINT, deleted VARCHAR DEFAULT 'FALSE');
CREATE TABLE users (id serial PRIMARY KEY, first_name VARCHAR, last_name varchar, staff_id VARCHAR UNIQUE, user_position VARCHAR, phone_no VARCHAR UNIQUE, email VARCHAR UNIQUE, photo VARCHAR, department_id INTEGER, created BIGINT, updated BIGINT, deleted VARCHAR DEFAULT 'FALSE');
CREATE TABLE posts (id serial PRIMARY KEY, title VARCHAR, content VARCHAR, type VARCHAR, user_id INTEGER, department_id INTEGER, created BIGINT, updated BIGINT, deleted VARCHAR DEFAULT 'FALSE');
CREATE TABLE user_departments (id serial PRIMARY KEY , user_id INTEGER, department_id INTEGER);
CREATE DATABASE organization_api_test WITH TEMPLATE organization_api;
select * from posts;
DROP DATABASE organization_api_test;

--TODO: remove department ID from users and create a standalone table --
CREATE TABLE user_departments (id serial PRIMARY KEY , user_id INTEGER, department_id INTEGER);

INSERT INTO departments (name, description, created) VALUES ('Marketing', 'Department in charge of promoting the business',123456789);

UPDATE departments SET deleted='TRUE' WHERE id=1;

truncate table posts;

DROP table users;

CREATE TABLE users (id serial PRIMARY KEY, first_name VARCHAR, last_name VARCHAR, staff_no VARCHAR UNIQUE, user_position VARCHAR, phone_no VARCHAR UNIQUE, email VARCHAR UNIQUE, photo VARCHAR, created BIGINT, updated BIGINT, deleted VARCHAR DEFAULT 'FALSE');



