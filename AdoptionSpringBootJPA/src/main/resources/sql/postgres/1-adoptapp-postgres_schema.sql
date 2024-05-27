--
-- PostgreSQL database dump
--
set ROLE larku;

DROP TABLE IF EXISTS adopter_pet;
-- DROP TABLE IF EXISTS pet_type;
DROP TABLE IF EXISTS adopter;
DROP TABLE IF EXISTS pet;

drop sequence if exists adopter_id_seq;
drop sequence if exists pet_id_seq;

-- Create Tables
CREATE TABLE adopter (
                         id          serial primary key NOT NULL,
                         name        VARCHAR(255) NOT NULL,
                         phone_number VARCHAR(12),
                         adoption_date date
);

CREATE TABLE pet (
                     id serial primary key NOT NULL,
                     name VARCHAR(255) NOT NULL,
--     type_id INTEGER,
                     type VARCHAR(255),
                     breed VARCHAR(255)
);

-- CREATE TABLE pet_type (
--     type_id serial primary key NOT NULL,
--     type_desc VARCHAR(255) NOT NULL
-- );

CREATE TABLE adopter_pet (
                             adopter_id INTEGER NOT NULL,
                             pet_id INTEGER NOT NULL
);


-- Add Constraints
-- ALTER TABLE ONLY pet
--     ADD CONSTRAINT fk_pet_pet_type FOREIGN KEY (type_id) REFERENCES pet_type (type_id);


ALTER TABLE adopter_pet
    ADD CONSTRAINT fk_adopter_pet_adopter_id FOREIGN KEY (adopter_id)
        REFERENCES adopter (id);

ALTER TABLE adopter_pet
    ADD CONSTRAINT fk_adopter_pet_pet_id FOREIGN KEY (pet_id)
        REFERENCES pet (id);

ALTER TABLE adopter_pet
    ADD CONSTRAINT NEW_UNIQUE UNIQUE (adopter_id, pet_id);

--
--Grant permissions
REVOKE ALL ON SCHEMA public FROM PUBLIC;

-- GRANT ALL ON SCHEMA public TO postgres;

-- GRANT ALL ON SCHEMA public TO postgres;
GRANT USAGE ON SCHEMA public TO larku;
GRANT SELECT, INSERT, UPDATE, DELETE, TRUNCATE on ALL TABLES IN SCHEMA public TO larku;
GRANT USAGE, SELECT, UPDATE on ALL SEQUENCES IN SCHEMA public TO larku;