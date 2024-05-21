-- Postgres larku schema file

DROP TABLE if exists Student_ScheduledClass;

DROP TABLE if exists ScheduledClass;

DROP TABLE if exists Course;

DROP TABLE if exists Student;


CREATE TABLE Student
(
    id          serial primary key not null,
    name        VARCHAR(255) NOT NULL,
    phoneNumber VARCHAR(20),
    dob         DATE,
    status      VARCHAR(20)
);

CREATE TABLE Course
(
     id      serial primary key not NULL,
     code    VARCHAR(20),
     credits REAL NOT NULL,
     title   VARCHAR(255)
);


CREATE TABLE ScheduledClass
(
     id        serial NOT NULL, 
     enddate   VARCHAR(20),
     startdate VARCHAR(20),
     course_id INTEGER
);

CREATE TABLE Student_ScheduledClass
(
     students_id INTEGER NOT NULL,
     classes_id  INTEGER NOT NULL
);
-- 
--CREATE UNIQUE INDEX SQL150211090953900 ON Course (id ASC);
CREATE UNIQUE INDEX idx_course_id ON Course (id ASC);
-- 
--CREATE INDEX SQL150211090954080 ON Student_ScheduledClass (classes_id ASC);
CREATE INDEX idx_student_scheduledclass_classes_id ON Student_ScheduledClass (classes_id ASC);

--CREATE INDEX SQL150211090954040 ON Student_ScheduledClass (students_id ASC);
CREATE INDEX idx_student_scheduledclass_students_id ON Student_ScheduledClass (students_id ASC);

--CREATE INDEX SQL150211090953990 ON ScheduledClass (course_id ASC);
CREATE INDEX idx_scheduledclass_course_id ON ScheduledClass (course_id ASC);
-- 
--CREATE UNIQUE INDEX SQL150211090953920 ON ScheduledClass (id ASC);
CREATE UNIQUE INDEX idx_scheduledclass_id ON ScheduledClass (id ASC);
-- 
--CREATE UNIQUE INDEX SQL150211090953950 ON Student (id ASC);
CREATE UNIQUE INDEX idx_student_id ON Student (id ASC);
-- 

--ALTER TABLE COURSE ADD CONSTRAINT SQL150211090953900 PRIMARY KEY (ID);

--ALTER TABLE SCHEDULEDCLASS ADD CONSTRAINT SQL150211090953920 PRIMARY KEY (ID);

--ALTER TABLE STUDENT ADD CONSTRAINT SQL150211090953950 PRIMARY KEY (ID);

ALTER TABLE Student_ScheduledClass
    ADD CONSTRAINT fk_student_scheduledclass_classes_id FOREIGN KEY (classes_id)
         REFERENCES ScheduledClass (id);
-- 
ALTER TABLE Student_ScheduledClass
     ADD CONSTRAINT fk_student_scheduled_class_students_id FOREIGN KEY (students_id)
         REFERENCES Student (id);
 
ALTER TABLE Student_ScheduledClass
     ADD CONSTRAINT NEW_UNIQUE UNIQUE (students_id, classes_id);
 
ALTER TABLE ScheduledClass
     ADD CONSTRAINT fk_scheduledclass_course_id FOREIGN KEY (course_id)
         REFERENCES Course (id);
 	
 
 
	
