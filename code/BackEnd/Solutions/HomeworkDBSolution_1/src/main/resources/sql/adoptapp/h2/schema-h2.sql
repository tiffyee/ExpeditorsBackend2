
DROP TABLE BIG_ADOPTER IF EXISTS;

CREATE USER IF NOT EXISTS LARKU SALT 'f2d97d5e5c194fe4' HASH 'bf9ac7082b79123183a1a58f3f23b3822cbedc5c1161394f43bd4d0d03237c59' ADMIN;

CREATE MEMORY TABLE BIG_ADOPTER(
    ID integer primary key auto_increment not null,
    NAME VARCHAR(255),
    PHONE_NUMBER VARCHAR(100),
    ADOPT_DATE DATE,
    PET_TYPE VARCHAR(25),
    PET_NAME VARCHAR(50),
    PET_BREED VARCHAR(50)
);



DROP TABLE BIG_ADOPTER_EMBEDDED IF EXISTS;

-- CREATE USER IF NOT EXISTS LARKU SALT 'f2d97d5e5c194fe4' HASH 'bf9ac7082b79123183a1a58f3f23b3822cbedc5c1161394f43bd4d0d03237c59' ADMIN;

CREATE MEMORY TABLE BIG_ADOPTER_EMBEDDED(
                                   ID integer primary key auto_increment not null,
                                   NAME VARCHAR(255),
                                   PHONE_NUMBER VARCHAR(100),
                                   ADOPT_DATE DATE,
                                   PET_TYPE VARCHAR(25),
                                   PET_NAME VARCHAR(50),
                                   PET_BREED VARCHAR(50)
);
