CREATE DATABASE organization_api;
CREATE TABLE departments (id serial PRIMARY KEY, name VARCHAR UNIQUE, description VARCHAR, created BIGINT, updated BIGINT, deleted VARCHAR DEFAULT 'FALSE');
CREATE TABLE users (id serial PRIMARY KEY, first_name VARCHAR, last_name varchar, staff_id VARCHAR UNIQUE, user_position VARCHAR, phone_no VARCHAR UNIQUE, email VARCHAR UNIQUE, photo VARCHAR, department_id INTEGER, created BIGINT, updated BIGINT, deleted VARCHAR DEFAULT 'FALSE');
CREATE TABLE posts (id serial PRIMARY KEY, title VARCHAR, content VARCHAR, type VARCHAR, user_id INTEGER, department_id INTEGER, created BIGINT, updated BIGINT, deleted VARCHAR DEFAULT 'FALSE');
CREATE TABLE user_departments (id serial PRIMARY KEY , user_id INTEGER, department_id INTEGER);
CREATE DATABASE organization_api_test WITH TEMPLATE organization_api;
select * from users;
DROP DATABASE organization_api_test;

truncate table departments;

--TODO: drop user_departments