-- We are creating a variable with the value of the
-- Environment variable DB_PASSWORD.  This *better*
-- have been set before this script is called.
--\set pw `echo ${DB_PASSWORD}`
-- \getenv pw DB_PASSWORD

-- \echo :pw

-- Here is where we use the variable set up above.
-- create user larku password :'pw' CREATEDB CREATEROLE;
-- Hard coded for now.
create user larku password 'larku' CREATEDB CREATEROLE;

ALTER ROLE larku WITH LOGIN;
ALTER ROLE larku INHERIT;

set role larku;

create database adoptapp;
create database larku;
create database northwind;
create database tracker;
