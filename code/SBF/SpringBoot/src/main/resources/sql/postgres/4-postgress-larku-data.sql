-- Postgres Larku data

-- SET ROLE postgres;

-- \c larku;
delete from student_scheduledclass;

delete from scheduledclass;
alter sequence scheduledclass_id_seq restart;

delete from course;
alter sequence course_id_seq restart;

delete from courseversioned;
alter sequence course_id_seq restart;

delete from student;
alter sequence student_id_seq restart;


INSERT INTO courseversioned (code, credits, title, version) VALUES
('BKTW-101',3.0,'Introduction to BasketWeaving', 1),
('BOT-202',2.0,'Yet more Botany', 1),
('MATH-101',4.0,'Intro To Math', 1);

INSERT INTO course (code, credits, title) VALUES
('BKTW-101',3.0,'Introduction to BasketWeaving'),
('BOT-202',2.0,'Yet more Botany'),
('MATH-101',4.0,'Intro To Math');

INSERT INTO scheduledClass (startdate, enddate, course_id) VALUES
('2022-10-10','2023-02-20',1),
('2022-10-10','2023-08-10',2),
('2022-10-10','2023-10-10',3);


INSERT INTO student (name,  dob, status) VALUES
('Manoj--Postgres', '1956-08-15', 'FULL_TIME'),
('Ana--Postgres','1978-03-10', 'PART_TIME'),
('Roberta--Postgres','2000-07-15', 'HIBERNATING'),
('Madhu--Postgres','1994-10-07', 'PART_TIME');

INSERT INTO phonenumber (type, number, student_id)
VALUES ('SATELLITE', '222 333-4444', 1),
       ('WORK', '8 987 49 7338', 1),
       ('MOBILE', '383 343-5879', 2),
       ('HOME', '898 76 8338279', 3);

INSERT INTO Student_ScheduledClass (students_id, classes_id) VALUES
(1,2),
(2,3),
(1,3);
