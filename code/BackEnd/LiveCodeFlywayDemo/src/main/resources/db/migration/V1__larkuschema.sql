drop table if exists scheduledclass;
drop table if exists course;
drop table if exists phonenumber;
drop table if exists student;

drop sequence if exists scheduledclass_id_seq;
drop sequence if exists course_id_seq;
drop sequence if exists phonenumber_id_seq;
drop sequence if exists student_id_seq;

create table public.course
(
    id      serial
        constraint course_pk
            primary key,
    code    varchar(20)  not null,
    title   varchar(100) not null,
    credits numeric(3, 1)
);

alter table public.course
    owner to larku;

create table public.scheduledclass
(
    id        serial
        constraint scheduledclass_pk
            primary key,
    startdate date,
    enddate   date,
    course_id integer
        constraint scheduledclass_course_id_fk
            references public.course
);

alter table public.scheduledclass
    owner to larku;

create table public.student
(
    id     serial
        constraint student_pk
            primary key,
    name   varchar(100) not null,
    dob    date,
    status varchar(20)
);

alter table public.student
    owner to larku;

create table public.phonenumber
(
    id         serial
        constraint phonenumber_pk
            primary key,
    type       varchar(20),
    student_id integer
        constraint phonenumber_student_id_fk
            references public.student,
    number     varchar(50)
);

alter table public.phonenumber
    owner to larku;

-- create sequence public.course_id_seq
--     as integer;

alter sequence public.course_id_seq owner to larku;

alter sequence public.course_id_seq owned by public.course.id;

-- create sequence public.scheduledclass_id_seq
--     as integer;

alter sequence public.scheduledclass_id_seq owner to larku;

alter sequence public.scheduledclass_id_seq owned by public.scheduledclass.id;

-- create sequence public.student_id_seq
--     as integer;

alter sequence public.student_id_seq owner to larku;

alter sequence public.student_id_seq owned by public.student.id;

-- create sequence public.phonenumber_id_seq
--     as integer;

alter sequence public.phonenumber_id_seq owner to larku;

alter sequence public.phonenumber_id_seq owned by public.phonenumber.id;

