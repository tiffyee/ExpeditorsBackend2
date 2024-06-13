-- Postgres Larku data

-- SET ROLE postgres;

\c tracker;
delete from TRACK;

INSERT INTO TRACK (TITLE, ARTIST, ALBUM, DURATION, FORMAT, DATE)
VALUES ('h2-The Shadow Of Your Smile', 'Big John Patton', 'Let them Roll', 375, 'CD', '1965-02-02'),
       ('h2-I''ll Remember April', 'Jim Hall and Ron Carter', 'Alone Together', 354, 'OGG', '1972-02-02'),
       ('h2-Leave It to Me', 'Herb Ellis', 'Three Guitars in Bossa Nova Time', 193, 'CD', '1963-02-02'),
       ('h2-Have you met Miss Jones', 'George Van Eps', 'Pioneers of the Electric Guitar', 138, 'MP3', '2013-02-02'),
       ('h2-My Funny Valentine', 'Johnny Smith', 'Moonlight in Vermont', 168, 'OGG', '1956-02-02'),
       ('h2-What''s New', 'John Coltrane', 'Ballads', 247, 'MP3', '1945-02-02');
