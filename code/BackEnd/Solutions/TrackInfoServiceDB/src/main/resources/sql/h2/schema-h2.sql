DROP TABLE if exists BIGTRACK;

CREATE USER IF NOT EXISTS TRACKER SALT 'f2d97d5e5c194fe4' HASH 'bf9ac7082b79123183a1a58f3f23b3822cbedc5c1161394f43bd4d0d03237c59' ADMIN;
CREATE MEMORY TABLE PUBLIC.BIGTRACK(
    ID BIGINT primary key auto_increment NOT NULL,
    TITLE VARCHAR(255),
    ARTIST VARCHAR(255),
    ALBUM VARCHAR(255),
    DURATION BIGINT,
    FORMAT VARCHAR(50),
    RELEASE_YEAR VARCHAR(255)
);

drop table if exists track_artist;
drop table if exists artist;
drop table if exists track;

create memory table artist
(
    artist_id integer primary key auto_increment not null,
    discogs_id varchar(255)
    constraint artist_discogs_id_unique
    unique,
    name       varchar(255),
    real_name  varchar(255),
    url        varchar(255),
    profile    varchar(10000)
    );

create table track
(
    track_id integer primary key auto_increment not null,
    title        varchar(255),
    album        varchar(255),
    band        varchar(100),
    imageUrl        varchar(300),
    duration     numeric(21),
    format       varchar(255),
    genre       varchar(255),
    release_year varchar(255)
    );

create table track_artist
(
    track_id    integer not null
    constraint fkp3qpu7m4npqt5q8byutno5bvm references track,

    artist_id integer not null
    constraint fk76nfjalp4aok5hlajhmsunrcf references artist,
    primary key (track_id, artist_id)
    );
