DROP TABLE IF EXISTS AUTHOR;
CREATE TABLE AUTHOR(
        ID BIGSERIAL,
        NAME VARCHAR(255) UNIQUE,
        PRIMARY KEY(ID));

DROP TABLE IF EXISTS GENRE;
CREATE TABLE GENRE(
        ID BIGSERIAL PRIMARY KEY,
        NAME VARCHAR(255) UNIQUE);


DROP TABLE IF EXISTS BOOK;
CREATE TABLE BOOK(
        ID BIGSERIAL PRIMARY KEY,
        NAME VARCHAR(255) UNIQUE,
        AUTHORID BIGINT REFERENCES AUTHOR(ID),
        GENREID BIGINT REFERENCES GENRE(ID));

DROP TABLE IF EXISTS COMMENT;
CREATE TABLE COMMENT(
        ID BIGSERIAL,
        COMMENT VARCHAR(255),
        BOOKID BIGINT REFERENCES BOOK(ID),
        PRIMARY KEY(ID));

create table avatars(
    id bigserial,
    photo_url varchar(8000),
    primary key (id)
);

create table courses(
    id bigserial,
    name varchar(255),
    primary key (id)
);

create table otus_students(
    id bigserial,
    name varchar(255),
    avatar_id bigint references avatars (id),
    primary key (id)
);

create table emails(
    id bigserial,
    student_id bigint references otus_students(id) on delete cascade,
    email varchar(255),
    primary key (id)
);

create table student_courses(
    student_id bigint references otus_students(id) on delete cascade,
    course_id bigint references courses(id),
    primary key (student_id, course_id)
);