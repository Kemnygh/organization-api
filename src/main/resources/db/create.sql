CREATE DATABASE organization_api;
CREATE TABLE departments (id serial PRIMARY KEY, name VARCHAR UNIQUE, description VARCHAR, created TIMESTAMP, updated TIMESTAMP, deleted VARCHAR DEFAULT 'FALSE');
CREATE TABLE users (id serial PRIMARY KEY, first_name VARCHAR, last_name varchar, staff_id VARCHAR UNIQUE, phone_no VARCHAR UNIQUE, email VARCHAR UNIQUE, department_id INTEGER, photo VARCHAR, created TIMESTAMP, updated TIMESTAMP, deleted VARCHAR DEFAULT 'FALSE');
CREATE TABLE posts (id serial PRIMARY KEY, title VARCHAR UNIQUE, content VARCHAR, user_id INTEGER, department_id INTEGER, created TIMESTAMP, updated TIMESTAMP, deleted VARCHAR DEFAULT 'FALSE');
CREATE DATABASE site_maintenance_test WITH TEMPLATE site_maintenance;
--select * from engineers;
DROP DATABASE organization_api_test;

--TODO: remove department ID from users and create a standalone table --
CREATE TABLE user_departments (id serail PRIMARY KEY , department_id INTEGER, user_id INTEGER)



