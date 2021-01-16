ALTER SESSION SET CURRENT_SCHEMA = GUI;


CREATE TABLE professor (
    id NUMBER(20) generated as identity primary key,
    email varchar(255) DEFAULT NULL,
    firstName varchar(255) DEFAULT NULL,
    lastName varchar(255) DEFAULT NULL,
    password varchar(255) DEFAULT NULL,
    role varchar(255) DEFAULT NULL
);

CREATE TABLE course (
    id NUMBER(20) generated as identity primary key,
    name varchar(255) DEFAULT NULL,
    professor_id NUMBER(20) DEFAULT NULL references PROFESSOR (id)
);

CREATE TABLE room (
    id NUMBER(20) generated as identity primary key,
    name varchar(255) DEFAULT NULL
);

CREATE TABLE lecture (
    id NUMBER(20) generated as identity primary key,
    dateTime TIMESTAMP DEFAULT NULL,
    room_id NUMBER(20) DEFAULT NULL REFERENCES room (id)
);

CREATE TABLE course_lectures (
                                  id NUMBER(20) generated as identity primary key,
                                  course_id NUMBER(20) NOT NULL REFERENCES course (id),
                                  lecture_id NUMBER(20) NOT NULL REFERENCES lecture (id)
);

CREATE TABLE student (
    id NUMBER(20) generated as identity primary key,
    email varchar(255) DEFAULT NULL,
    firstName varchar(255) DEFAULT NULL,
    lastName varchar(255) DEFAULT NULL,
    password varchar(255) DEFAULT NULL,
    role varchar(255) DEFAULT NULL,
    degree varchar(255) DEFAULT NULL
);

CREATE TABLE courses_students (
    id NUMBER(20) generated as identity primary key,
    course_id NUMBER(20) NOT NULL REFERENCES course (id),
    student_id NUMBER(20) NOT NULL REFERENCES student (id)
);
