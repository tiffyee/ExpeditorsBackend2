drop table if exists student_scheduledclass;
drop table if exists scheduledclass;
drop table if exists course;
drop table if exists courseversioned;
drop table if exists student_phones;
drop table if exists phonenumber;
drop table if exists student;

drop sequence if exists scheduledclass_id_seq;
drop sequence if exists course_id_seq;
drop sequence if exists courseversioned_id_seq;
drop sequence if exists phonenumber_id_seq;
drop sequence if exists student_id_seq;

create table public.courseversioned
(
    id      serial
        constraint courseversioned_pk
            primary key,
    version  integer not null,
    code    varchar(20)  not null,
    title   varchar(100) not null,
    credits numeric(3, 1)
);

alter table public.courseversioned
    owner to larku;

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
    number     varchar(50),
    student_id integer
        constraint phonenumber_student_id_fk
            references public.student
);


alter table public.phonenumber
    owner to larku;

create table public.student_scheduledclass
(
    students_id integer
        constraint student_scheduledclass_student_id_fk
            references public.student,
    classes_id   integer
        constraint student_scheduledclass_scheduledclass_id_fk
            references public.scheduledclass,
    constraint student_scheduledclass_pk
        unique (classes_id, students_id)
);

alter table public.student_scheduledclass
    owner to larku;

create table public.student_phones
(
    student_id integer
        constraint student_phone_student_id_fk
            references public.student,
    phone_id   integer
        constraint student_phone_phonenumber_id_fk
            references public.phonenumber,
    constraint student_phone_pk
        unique (phone_id, student_id)
);

alter table public.student_phones
    owner to larku;




