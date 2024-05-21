-- Mysql Data

USE adoptapp;

-- For everything in one table
INSERT INTO BIG_ADOPTER (NAME, PHONE_NUMBER, ADOPT_DATE, PET_TYPE, PET_NAME, PET_BREED)
VALUES ('MysqlBA-Joey', '383 9999 9393', '1960-06-09', 'DOG', 'woofie', 'mixed'),
       ('MysqlBA-Francine', '383 9339 9999 9393', '2020-05-09', 'DOG', 'slinky', 'dalmation'),
       ('MysqlBA-Darlene', '4484 9339 77939', '2020-05-09', 'TURTLE', 'swifty', null),
       ('MysqlBA-Miguel', '77 888 93938', '2022-03-09', 'DOG', 'woofwoof', 'Terrier');

-- For the @EntityCollection example, with an @Embeddable Pet class
INSERT INTO BIG_ADOPTER_EMBEDDED (NAME, PHONE_NUMBER)
VALUES ('MysqlAEM-Joey', '383 9999 9393'),
       ('MysqlAEM-Francine', '383 9339 9999 9393'),
       ('MysqlAEM-Darlene', '4484 9339 77939'),
       ('MysqlAEM-Miguel', '77 888 93938');

INSERT INTO BIG_ADOPTER_PET (PET_TYPE, PET_NAME, PET_BREED, ADOPT_DATE, ADOPTER_ID)
VALUES ('DOG', 'woofie', 'mixed', '1959-06-09', 1),
       ('DOG', 'slinky', 'dalmation', '2020-05-09', 1),
       ('DOG', 'woofwoof', 'Terrier', '2020-05-09', 2),
       ('TURTLE', 'swifty', null, '2022-03-09', 3);

-- For the case where both the Adopter and the Pet are
-- Entities
INSERT INTO ADOPTER (NAME, PHONE_NUMBER)
VALUES ('MysqlA-Joey', '383 9999 9393'),
       ('MysqlA-Francine', '383 9339 9999 9393'),
       ('MysqlA-Darlene', '4484 9339 77939'),
       ('MysqlA-Miguel', '77 888 93938');


INSERT INTO PET (PET_TYPE, PET_NAME, PET_BREED, ADOPT_DATE, ADOPTER_ID)
VALUES ('DOG', 'woofie', 'mixed', '1959-06-09', 1),
       ('DOG', 'slinky', 'dalmation', '2020-05-09', 1),
       ('DOG', 'woofwoof', 'Terrier', '2020-05-09', 2),
       ('TURTLE', 'swifty', null, '2022-03-09', 3);
