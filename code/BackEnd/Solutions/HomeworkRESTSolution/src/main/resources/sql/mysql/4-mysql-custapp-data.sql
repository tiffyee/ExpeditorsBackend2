-- Mysql Data

USE larku;

INSERT INTO Customer (NAME, PHONENUMBER, DOB, STATUS) VALUES
('Manoj-Mysql','222 333-4444','1956-08-15', 'FULL_TIME'),
('Ana-Mysql','222 333-7900','1978-03-10', 'PART_TIME'),
('Roberta-Mysql','383 343-5879','2000-07-15', 'HIBERNATING'),
('Madhu-Mysql','383 598-8279','1994-10-07', 'PART_TIME');

INSERT INTO Student_ScheduledClass (students_id,classes_id) VALUES
(1,2),
(2,3),
(1,3);
