INSERT INTO Genre (name) VALUES ('детектив');
INSERT INTO Genre (name) VALUES ('роман');
INSERT INTO Genre (name) VALUES ('драма');

INSERT INTO Author (name) VALUES ('А.Пушкин');
INSERT INTO Author (name) VALUES ('Л.Толстой');
INSERT INTO Author (name) VALUES ('Ф.Достоевский');
INSERT INTO Author (name) VALUES ('М.Лермонтов');


INSERT INTO Book (name, AUTHORID, GENREID)
VALUES ('Герой нашего времени', (SELECT id FROM Author WHERE name = 'М.Лермонтов'), (SELECT id FROM Genre WHERE name = 'роман'));

INSERT INTO Book (name, AUTHORID, GENREID)
VALUES ('Война и мир', (SELECT id FROM Author WHERE name = 'Л.Толстой'),(SELECT id FROM Genre WHERE name = 'роман'));

INSERT INTO Book (name, AUTHORID, GENREID)
VALUES ('Преступление и наказание', (SELECT id FROM Author WHERE name = 'Ф.Достоевский'), (SELECT id FROM Genre WHERE name = 'драма'));

INSERT INTO Book (name, AUTHORID, GENREID)
VALUES ('Идиот', (SELECT id FROM Author WHERE name = 'Ф.Достоевский'), (SELECT id FROM Genre WHERE name = 'драма'));

INSERT INTO Book (name, AUTHORID, GENREID)
VALUES ('Евгений Онегин', (SELECT id FROM Author WHERE name = 'А.Пушкин'), (SELECT id FROM Genre WHERE name = 'роман'));

INSERT INTO Book (name, AUTHORID, GENREID)
VALUES ('Братья Карамазовы', (SELECT id FROM Author WHERE name = 'Ф.Достоевский'), (SELECT id FROM Genre WHERE name = 'драма'));

INSERT INTO comment (comment, BOOKID)
VALUES ('супер',(SELECT id FROM Book WHERE name = 'Братья Карамазовы'));

INSERT INTO comment (comment, BOOKID)
VALUES ('Мастрид',(SELECT id FROM Book WHERE name = 'Война и мир'));

INSERT INTO comment (comment, BOOKID)
VALUES ('супер-пупер',(SELECT id FROM Book WHERE name = 'Идиот'));