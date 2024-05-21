-- Mysql Larku Schema

DROP DATABASE IF EXISTS custapp;

CREATE DATABASE IF NOT EXISTS custapp;

USE custapp;

DROP TABLE if exists Customer;


CREATE TABLE Customer
(
    id          INT(11)      NOT NULL AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL,
    phoneNumber VARCHAR(20),
    dob         DATE,
    status      VARCHAR(20),
    CONSTRAINT student_pk PRIMARY KEY (id)
);