CREATE DATABASE organization_api;
CREATE TABLE departments (id serial PRIMARY KEY, name VARCHAR UNIQUE, description VARCHAR, created BIGINT, updated BIGINT, deleted VARCHAR DEFAULT 'FALSE');
CREATE TABLE users (id serial PRIMARY KEY, first_name VARCHAR, last_name varchar, staff_id VARCHAR UNIQUE, phone_no VARCHAR UNIQUE, email VARCHAR UNIQUE, photo VARCHAR, created BIGINT, updated BIGINT, deleted VARCHAR DEFAULT 'FALSE');
CREATE TABLE posts (id serial PRIMARY KEY, title VARCHAR UNIQUE, content VARCHAR, user_id INTEGER, department_id INTEGER, created BIGINT, updated BIGINT, deleted VARCHAR DEFAULT 'FALSE');
CREATE DATABASE organization_api_test WITH TEMPLATE organization_api;
--select * from engineers;
DROP DATABASE organization_api_test;

--TODO: remove department ID from users and create a standalone table --
CREATE TABLE user_departments (id serial PRIMARY KEY , department_id INTEGER, user_id INTEGER)



