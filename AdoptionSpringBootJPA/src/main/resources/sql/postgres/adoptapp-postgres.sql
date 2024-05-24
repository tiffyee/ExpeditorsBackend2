--
-- PostgreSQL database dump
--
set ROLE larku;

DROP TABLE IF EXISTS adopted_pet;
-- DROP TABLE IF EXISTS pet_type;
DROP TABLE IF EXISTS adopter;
DROP TABLE IF EXISTS pet;

drop sequence if exists adopter_id_seq;
drop sequence if exists pet_id_seq;

-- Create Tables
CREATE TABLE adopter (
    id          serial primary key NOT NULL,
    name        VARCHAR(255) NOT NULL,
    phoneNumber VARCHAR(12)

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

CREATE TABLE adopted_pet (
    adopter_id INTEGER NOT NULL,
    pet_id INTEGER NOT NULL,
    adoptionDate date

);


--
-- Data for Name: adopter; Type: TABLE DATA; Schema: public; Owner: -
--
--
-- INSERT INTO adopter VALUES (1, 'John Doe', '333-333-3333');
-- INSERT INTO adopter VALUES (2, 'Jane Smith', '123-456-7890');
-- INSERT INTO adopter VALUES (3, 'Tiffany Yee', '555-555-5555');
--
-- -- INSERT INTO adopter VALUES (1, 'John Doe', '333-333-3333');
-- -- INSERT INTO adopter VALUES (2, 'Jane Smith', '123-456-7890');
-- -- INSERT INTO adopter VALUES (3, 'Tiffany Yee', '555-555-5555');
--
-- --
-- -- Data for Name: pet; Type: TABLE DATA; Schema: public; Owner: -
-- --
-- INSERT INTO pet VALUES (1, 'Bobo', 'CAT', 'Tabby');
-- INSERT INTO pet VALUES (2, 'Frankie', 'TURTLE', 'Red-Eared Slider');
-- INSERT INTO pet VALUES (3, 'Moose', 'DOG', 'Bernadoodle');
-- INSERT INTO pet VALUES (4, 'Jude', 'DOG', 'Golden Retriever');
--
--
-- --
-- -- Data for Name: pet_type; Type: TABLE DATA; Schema: public; Owner: -
-- --
--
-- -- INSERT INTO pet_type VALUES (1, 'CAT');
-- -- INSERT INTO pet_type VALUES (2, 'DOG');
-- -- INSERT INTO pet_type VALUES (3, 'TURTLE');
--
--
-- --
-- -- Data for Name: adopted_pet; Type: TABLE DATA; Schema: public; Owner: -
-- --
--
-- INSERT INTO adopted_pet VALUES (1, 1, '2002-10-15');
-- INSERT INTO adopted_pet VALUES (2, 2, '2016-07-15');
-- INSERT INTO adopted_pet VALUES (3, 3, '2022-03-21');
-- INSERT INTO adopted_pet VALUES (1, 4, '2013-12-12');

-- Add Constraints
-- ALTER TABLE ONLY pet
--     ADD CONSTRAINT fk_pet_pet_type FOREIGN KEY (type_id) REFERENCES pet_type (type_id);


ALTER TABLE adopted_pet
    ADD CONSTRAINT fk_adopted_pet_adopter_id FOREIGN KEY (adopter_id)
        REFERENCES adopter (id);

ALTER TABLE adopted_pet
    ADD CONSTRAINT fk_adopted_pet_pet_id FOREIGN KEY (pet_id)
        REFERENCES pet (id);

ALTER TABLE adopted_pet
    ADD CONSTRAINT NEW_UNIQUE UNIQUE (adopter_id, pet_id);

--
--Grant permissions
REVOKE ALL ON SCHEMA public FROM PUBLIC;

-- GRANT ALL ON SCHEMA public TO postgres;

-- GRANT ALL ON SCHEMA public TO postgres;
GRANT USAGE ON SCHEMA public TO larku;
GRANT SELECT, INSERT, UPDATE, DELETE, TRUNCATE on ALL TABLES IN SCHEMA public TO larku;
GRANT USAGE, SELECT, UPDATE on ALL SEQUENCES IN SCHEMA public TO larku;