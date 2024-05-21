-- Mysql Larku Schema

DROP DATABASE IF EXISTS adoptapp;

CREATE DATABASE IF NOT EXISTS adoptapp;

USE adoptapp;

-- Everything in One table.  The Adopter can
-- have only 1 pet.
DROP TABLE if exists BIG_ADOPTER;

CREATE TABLE BIG_ADOPTER
(
    ID integer primary key auto_increment not null,
    NAME VARCHAR(255),
    PHONE_NUMBER VARCHAR(100),
    ADOPT_DATE DATE,
    PET_TYPE VARCHAR(25),
    PET_NAME VARCHAR(50),
    PET_BREED VARCHAR(50)

);

-- This if for the "@ElementCollection".  Does not
-- really buy us much if we are going to make the tables
-- anyway.  The big difference between this Embedded version
-- and the next one below with the Pet as an Entity, is
-- that in this case the Pet table does not have an 'id'
-- column.  It does not have an independent identity -
-- you can't search for a Pet by id, for example.
DROP TABLE if exists BIG_ADOPTER_PET;
DROP TABLE if exists BIG_ADOPTER_EMBEDDED;

CREATE TABLE BIG_ADOPTER_EMBEDDED
(
    ID integer primary key auto_increment not null,
    NAME VARCHAR(255),
    PHONE_NUMBER VARCHAR(100)
);

CREATE TABLE BIG_ADOPTER_PET
(
    PET_TYPE VARCHAR(25),
    PET_NAME VARCHAR(50),
    PET_BREED VARCHAR(50),
    ADOPT_DATE DATE,

    ADOPTER_ID INTEGER NOT NULL,

    CONSTRAINT FK_ADOPTER_EMBEDDED FOREIGN KEY(ADOPTER_ID) REFERENCES BIG_ADOPTER_EMBEDDED(ID)
);


-- In this case, the PET is an Entity in its own right, and
-- has an 'id' column.  It can exist independently of the
-- Adopter.

DROP TABLE if exists PET;
DROP TABLE if exists ADOPTER;

CREATE TABLE ADOPTER
(
    ID integer primary key auto_increment not null,
    NAME VARCHAR(255),
    PHONE_NUMBER VARCHAR(100)
);

CREATE TABLE PET
(
    PET_ID integer primary key auto_increment not null,
    PET_TYPE VARCHAR(25),
    PET_NAME VARCHAR(50),
    PET_BREED VARCHAR(50),
    ADOPT_DATE DATE,

    ADOPTER_ID INTEGER,

    CONSTRAINT FK_ADOPTER FOREIGN KEY(ADOPTER_ID) REFERENCES ADOPTER(ID)
);
