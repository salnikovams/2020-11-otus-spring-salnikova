DROP TABLE IF EXISTS AUTHOR;
CREATE TABLE AUTHOR(ID SERIAL PRIMARY KEY, NAME VARCHAR(255) UNIQUE);

DROP TABLE IF EXISTS GENRE;
CREATE TABLE GENRE(ID SERIAL PRIMARY KEY, NAME VARCHAR(255) UNIQUE);

DROP TABLE IF EXISTS BOOK;
CREATE TABLE BOOK(
    ID SERIAL PRIMARY KEY,
    NAME VARCHAR(255),
    AUTHORID BIGINT REFERENCES AUTHOR(ID),
    GENREID BIGINT REFERENCES GENRE(ID));
