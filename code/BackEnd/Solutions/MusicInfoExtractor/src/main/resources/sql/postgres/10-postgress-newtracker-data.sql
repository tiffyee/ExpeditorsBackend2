-- Postgres Larku data

-- SET ROLE postgres;

\c tracker;

insert into track (track_id, title, album, band, duration, format, reLease_year )
VALUES (1, 'h2-The Shadow Of Your Smile', 'Let them Roll', 'Big John Patton', 375000000000, 'CD', '1965'),
       (2, 'h2-I''ll Remember April', 'Alone Together', 'Jim Hall and Ron Carter', 354000000000, 'OGG', '1972'),
       (3, 'h2-Leave It to Me', 'Three Guitars in Bossa Nova Time', 'Herb Ellis', 193000000000, 'CD', '1963'),
       (4, 'h2-Have you met Miss Jones', 'Pioneers of the Electric Guitar', 'George Van Eps', 138000000000, 'MP3', '2013'),
       (5, 'h2-My Funny Valentine', 'Moonlight in Vermont', 'Johnny Smith', 168000000000, 'OGG', '1956'),
       (6, 'h2-What''s New', 'Ballads', 'John Coltrane', 247000000000, 'MP3', '1945-02-02');

INSERT INTO ARTIST (artist_id, name)
VALUES (1, 'Big John Patton'),
       (2, 'Jim Hall and Ron Carter'),
       (3, 'Herb Ellis'),
       (4, 'George Van Eps'),
       (5, 'Johnny Smith'),
       (6, 'John Coltrane');

insert into track_artist
VALUES (1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6);