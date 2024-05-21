-- BigAdopter.  Everything is in one table, so an Adopter can
-- adopt only one pet.
DROP TABLE BIG_ADOPTER IF EXISTS;

CREATE USER IF NOT EXISTS LARKU SALT 'f2d97d5e5c194fe4' HASH 'bf9ac7082b79123183a1a58f3f23b3822cbedc5c1161394f43bd4d0d03237c59' ADMIN;

-- Everything in One table.  The Adopter can
-- have only 1 pet.
CREATE MEMORY TABLE BIG_ADOPTER
(
    ID           integer primary key auto_increment not null,
    NAME         VARCHAR(255),
    PHONE_NUMBER VARCHAR(100),
    ADOPT_DATE   DATE,
    PET_TYPE     VARCHAR(25),
    PET_NAME     VARCHAR(50),
    PET_BREED    VARCHAR(50)
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
    ID serial primary key not null,
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

    CONSTRAINT FK_ADOPTER FOREIGN KEY(ADOPTER_ID) REFERENCES BIG_ADOPTER_EMBEDDED(ID)
);


-- Adopter.  Tables are split up to allow a one to many
-- relationship. Pet is an Entity in it's own right.
DROP TABLE PET IF EXISTS;
DROP TABLE ADOPTER IF EXISTS;

CREATE
    MEMORY TABLE ADOPTER
(
    ID           integer primary key auto_increment not null,
    NAME         VARCHAR(255),
    PHONE_NUMBER VARCHAR(100)
);

CREATE
    MEMORY TABLE PET
(
    PET_ID     integer primary key auto_increment not null,
    PET_TYPE   VARCHAR(25),
    PET_NAME   VARCHAR(50),
    PET_BREED  VARCHAR(50),
    ADOPT_DATE DATE,

    ADOPTER_ID INTEGER
);


ALTER TABLE PET
    ADD CONSTRAINT PUBLIC.PET FOREIGN KEY (ADOPTER_ID) REFERENCES ADOPTER (ID);
ALTER TABLE PET
    ADD CONSTRAINT PUBLIC.PET_TO_ADOPTER UNIQUE (PET_ID, ADOPTER_ID);
