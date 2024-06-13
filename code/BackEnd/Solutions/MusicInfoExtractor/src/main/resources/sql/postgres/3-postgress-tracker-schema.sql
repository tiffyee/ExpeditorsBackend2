-- Postgres larku schema file
-- Should run as 'admin' user (postgres)

-- SET ROLE postgres;

-- Note that the database 'larku' and the user 'larku' have to already exist

-- select current_user;

DROP TABLE if exists Track;

-- Create Tables
CREATE TABLE TRACK
(
    ID          serial primary key not null,
    TITLE       VARCHAR(255) NOT NULL,
    ARTIST      VARCHAR(255),
    ALBUM      VARCHAR(255),
    DURATION      BIGINT,
    FORMAT         VARCHAR(255),
    RELEASE_YEAR      VARCHAR(20)
);

--
 
 
GRANT ALL ON SCHEMA public TO postgres;

-- GRANT ALL ON SCHEMA public TO postgres;
GRANT USAGE ON SCHEMA public TO larku;
GRANT SELECT, INSERT, UPDATE, DELETE, TRUNCATE on ALL TABLES IN SCHEMA public TO larku;
GRANT USAGE, SELECT, UPDATE on ALL SEQUENCES IN SCHEMA public TO larku;
