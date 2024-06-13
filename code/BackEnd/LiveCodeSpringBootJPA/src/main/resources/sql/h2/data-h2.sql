INSERT INTO COURSE (CODE, CREDITS, TITLE)
VALUES ('BKTW-101', 3.0, 'Introduction to BasketWeaving'),
       ('BOT-202', 2.0, 'Yet more Botany'),
       ('MATH-101', 4.0, 'Intro To Math');

INSERT INTO SCHEDULEDCLASS (STARTDATE, ENDDATE, COURSE_ID)
VALUES ('2022-10-10', '2023-2-20', 1),
       ('2022-10-10', '2023-08-10', 2),
       ('2022-10-10', '2023-10-10', 3);

-- INSERT INTO STUDENT (NAME,PHONENUMBER,DOB, STATUS) VALUES
-- ('Manoj-h2','222 333-4444','1956-08-15', 'FULL_TIME'),
-- ('Ana-h2','222 333-7900','1978-03-10', 'PART_TIME'),
-- ('Roberta-h2','383 343-5879','2000-07-15', 'HIBERNATING'),
-- ('Madhu-h2','383 598-8279','1994-10-07', 'PART_TIME');
INSERT INTO student (name, dob, status)

VALUES ('Manoj--h2', '1956-08-15', 'FULL_TIME'),
       ('Ana--h2', '1978-03-10', 'PART_TIME'),
       ('Roberta--h2', '2000-07-15', 'HIBERNATING'),
       ('Madhu--h2', '1994-10-07', 'PART_TIME');

INSERT INTO phonenumber (type, number, student_id)
VALUES ('SATELLITE', '222 333-4444', 1),
       ('WORK', '8 987 49 7338', 1),
       ('MOBILE', '383 343-5879', 2),
       ('HOME', '898 76 8338279', 3);

INSERT INTO STUDENT_SCHEDULEDCLASS (STUDENTS_ID, CLASSES_ID)
VALUES (1, 2),
       (2, 3),
       (1, 3);