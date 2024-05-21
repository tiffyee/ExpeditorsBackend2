-- Postgres custapp schema file

SET ROLE larku;

DROP TABLE if exists Phonenumber;
DROP TABLE if exists Customer;

CREATE TABLE Customer
(
    id          serial primary key not null,
    name        VARCHAR(255) NOT NULL,
    phoneNumber VARCHAR(20),
    dob         DATE,
    status      VARCHAR(20)
);

 
 
	
