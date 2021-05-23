insert into AUTHOR(name)
values ('Ivanov'), ('Petrov');


insert into GENRE(name)
values ('detective'), ('fantasy');

insert into BOOK(name, authorid, genreid) values ('book number one',1, 1), ('book number two', 2, 2);

insert into COMMENT(comment, bookid)
values ('comment1', 1), ('comment2', 1);

