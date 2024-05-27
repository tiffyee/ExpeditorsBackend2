delete from adopter_pets;

delete from adopter;
alter sequence adopter_id_seq restart;

delete from pet;
alter sequence pet_id_seq restart;




-- Data for Name: adopter; Type: TABLE DATA; Schema: public; Owner: -


INSERT INTO adopter (id, name, phonenumber, adoptiondate) VALUES
(1, 'John Doe', '333-333-3333', '2002-10-15'),
(2, 'Jane Smith', '123-456-7890', '2016-07-15'),
(3, 'Tiffany Yee', '555-555-5555', '2022-03-21');

--
-- Data for Name: pet; Type: TABLE DATA; Schema: public; Owner: -
--
INSERT INTO pet (id, name, type, breed) VALUES
(1, 'Bobo', 'CAT', 'Tabby'),
(2, 'Frankie', 'TURTLE', 'Red-Eared Slider'),
(3, 'Moose', 'DOG', 'Bernadoodle'),
(4, 'Jude', 'DOG', 'Golden Retriever');


--
-- Data for Name: pet_type; Type: TABLE DATA; Schema: public; Owner: -
--

-- INSERT INTO pet_type VALUES (1, 'CAT');
-- INSERT INTO pet_type VALUES (2, 'DOG');
-- INSERT INTO pet_type VALUES (3, 'TURTLE');


--
-- Data for Name: adopter_pets; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO adopter_pets (adopter_id, pets_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(1, 4);