--User Data

INSERT INTO users (username, password, enabled) values
('manoj','{bcrypt}$2a$10$2KtlBG9s4.QckPpFaM13b.3jeC6D/gYcWxFOcCVkpk7k/9Iz03THy', true),
('ana','{bcrypt}$2a$10$2KtlBG9s4.QckPpFaM13b.3jeC6D/gYcWxFOcCVkpk7k/9Iz03THy', true),
('roberta','{bcrypt}$2a$10$2KtlBG9s4.QckPpFaM13b.3jeC6D/gYcWxFOcCVkpk7k/9Iz03THy', true),
('madhu','{bcrypt}$2a$10$2KtlBG9s4.QckPpFaM13b.3jeC6D/gYcWxFOcCVkpk7k/9Iz03THy', true),
('bobby','{bcrypt}$2a$10$2KtlBG9s4.QckPpFaM13b.3jeC6D/gYcWxFOcCVkpk7k/9Iz03THy', true);

INSERT INTO authorities (username, authority) values
('manoj','ROLE_USER'),
('ana','ROLE_USER'),
('roberta','ROLE_USER'),
('madhu','ROLE_USER'),
('bobby','ROLE_USER'),
('bobby','ROLE_ADMIN');
