-- Mysql Data

USE adoptapp;

INSERT INTO BIG_ADOPTER (NAME, PHONE_NUMBER, ADOPT_DATE, PET_TYPE, PET_NAME, PET_BREED) VALUES
('Mysql-Joey', '383 9999 9393', '1960-06-09', 'DOG', 'woofie', 'mixed'),
('Mysql-Francine', '383 9339 9999 9393', '2020-05-09', 'DOG', 'slinky', 'dalmation'),
('Mysql-Darlene', '4484 9339 77939', '2020-05-09', 'TURTLE', 'swifty', null),
('Mysql-Miguel', '77 888 93938', '2022-03-09', 'DOG', 'woofwoof', 'Terrier');

INSERT INTO BIG_ADOPTER_EMBEDDED (NAME, PHONE_NUMBER, ADOPT_DATE, PET_TYPE, PET_NAME, PET_BREED)
VALUES
('MysqlEm-Joey', '383 9999 9393', '1960-06-09', 'DOG', 'woofie', 'mixed'),
('MysqlEm-Francine', '383 9339 9999 9393', '2020-05-09', 'DOG', 'slinky', 'dalmation'),
('MysqlEm-Darlene', '4484 9339 77939', '2020-05-09', 'TURTLE', 'swifty', null),
('MysqlEm-Miguel', '77 888 93938', '2022-03-09', 'DOG', 'woofwoof', 'Terrier');
