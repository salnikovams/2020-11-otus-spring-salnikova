insert into author (id, `name`) values (1, 'Jack London');
--insert into author (id, `name`) values (2, 'Alexander Duma');
--insert into author (id, `name`) values (3, 'Mark Twain');

insert into genre (id, `name`) values (1, 'Adventure');
--insert into genre (id, `name`) values (2, 'Hystoric');

insert into book (id, name, AUTHORID, GENREID) values (1, 'The Call of the Wild', 1, 1);
--insert into book (id, name, AUTHORID, GENREID) values (2, 'The three Musketeers', 2, 2);
--insert into book (id, name, AUTHORID, GENREID) values (3, 'The Adventures of Tom Sawyer', 3, 1);
