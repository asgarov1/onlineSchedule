ALTER SESSION SET CURRENT_SCHEMA = GUI;

--CREATE PROCEDURES

CREATE TABLE professor
(
    id        NUMBER(20) generated as identity primary key,
    email     varchar(255) DEFAULT NULL,
    firstName varchar(255) DEFAULT NULL,
    lastName  varchar(255) DEFAULT NULL,
    password  varchar(255) DEFAULT NULL,
    role      varchar(255) DEFAULT NULL
);

CREATE TABLE course
(
    id           NUMBER(20) generated as identity primary key,
    name         varchar(255) DEFAULT NULL,
    professor_id NUMBER(20)   DEFAULT NULL references PROFESSOR (id)
);

CREATE TABLE room
(
    id   NUMBER(20) generated as identity primary key,
    name varchar(255) DEFAULT NULL
);

CREATE TABLE lecture
(
    id       NUMBER(20) generated as identity primary key,
    dateTime TIMESTAMP  DEFAULT NULL,
    room_id  NUMBER(20) DEFAULT NULL REFERENCES room (id)
);

CREATE TABLE course_lectures
(
    id         NUMBER(20) generated as identity primary key,
    course_id  NUMBER(20) NOT NULL REFERENCES course (id),
    lecture_id NUMBER(20) NOT NULL REFERENCES lecture (id)
);

CREATE TABLE student
(
    id        NUMBER(20) generated as identity primary key,
    email     varchar(255) DEFAULT NULL,
    firstName varchar(255) DEFAULT NULL,
    lastName  varchar(255) DEFAULT NULL,
    password  varchar(255) DEFAULT NULL,
    role      varchar(255) DEFAULT NULL,
    degree    varchar(255) DEFAULT NULL
);

CREATE TABLE courses_students
(
    id         NUMBER(20) generated as identity primary key,
    course_id  NUMBER(20) NOT NULL REFERENCES course (id),
    student_id NUMBER(20) NOT NULL REFERENCES student (id)
);

CREATE OR REPLACE PROCEDURE create_course(p_name IN COURSE.NAME%TYPE,
                                          p_professor_id IN PROFESSOR.ID%TYPE,
                                          p_id OUT COURSE.ID%TYPE)
AS
BEGIN
    INSERT INTO COURSE (NAME, PROFESSOR_ID)
    VALUES (p_name, p_professor_id)
    returning id into p_id;
end;
/

CREATE OR REPLACE PROCEDURE create_course_lectures(p_course_id IN course_lectures.course_id%TYPE,
                                                   p_lecture_id IN course_lectures.lecture_id%TYPE,
                                                   p_id OUT course_lectures.id%TYPE)
AS
BEGIN
    INSERT INTO course_lectures (course_id, lecture_id)
    VALUES (p_course_id, p_lecture_id)
    returning id into p_id;
end;
/

CREATE OR REPLACE PROCEDURE create_courses_students(p_course_id IN courses_students.course_id%TYPE,
                                                    p_student_id IN courses_students.student_id%TYPE,
                                                    p_id OUT courses_students.id%TYPE)
AS
BEGIN
    INSERT INTO courses_students (course_id, student_id)
    VALUES (p_course_id, p_student_id)
    returning id into p_id;
end;
/

CREATE OR REPLACE PROCEDURE create_lecture(p_datetime IN lecture.dateTime%TYPE,
                                           p_room_id IN room.id%TYPE,
                                           p_id OUT lecture.id%TYPE)
AS
BEGIN
    INSERT INTO lecture (DATETIME, ROOM_ID)
    VALUES (p_datetime, p_room_id)
    returning id into p_id;
end;
/

CREATE OR REPLACE PROCEDURE create_professor(p_email IN professor.email%TYPE,
                                             p_firstname IN professor.firstName%TYPE,
                                             p_lastname IN professor.lastName%TYPE,
                                             p_password IN professor.password%TYPE,
                                             p_role IN professor.role%TYPE,
                                             p_id OUT professor.id%TYPE)
AS
BEGIN
    INSERT INTO professor (email, firstName, lastName, password, role)
    VALUES (p_email, p_firstname, p_lastname, p_password, p_role)
    returning id into p_id;
end;
/

CREATE OR REPLACE PROCEDURE create_student(p_email IN student.email%TYPE,
                                           p_firstname IN student.firstName%TYPE,
                                           p_lastname IN student.lastName%TYPE,
                                           p_password IN student.password%TYPE,
                                           p_role IN student.role%TYPE,
                                           p_degree IN student.degree%TYPE,
                                           p_id OUT student.id%TYPE)
AS
BEGIN
    INSERT INTO student (email, firstName, lastName, password, role, degree)
    VALUES (p_email, p_firstname, p_lastname, p_password, p_role, p_degree)
    returning id into p_id;
end;
/

CREATE OR REPLACE PROCEDURE create_room(p_name IN room.name%TYPE,
                                        p_id OUT room.id%TYPE)
AS
BEGIN
    INSERT INTO room (name)
    VALUES (p_name)
    returning id into p_id;
end;
/

--- UPDATE PROCEDURES

CREATE OR REPLACE PROCEDURE update_course(p_name IN COURSE.NAME%TYPE,
                                          p_professor_id IN PROFESSOR.ID%TYPE,
                                          p_id IN COURSE.ID%TYPE)
AS
BEGIN
    UPDATE COURSE
    SET NAME         = p_name,
        PROFESSOR_ID = p_professor_id
    where ID = p_id;
end;
/

CREATE OR REPLACE PROCEDURE update_course_lectures(p_course_id IN course_lectures.course_id%TYPE,
                                                   p_lecture_id IN course_lectures.lecture_id%TYPE,
                                                   p_id IN course_lectures.id%TYPE)
AS
BEGIN
    UPDATE course_lectures
    SET course_id  = p_course_id,
        lecture_id = p_lecture_id
    where id = p_id;
end;
/

CREATE OR REPLACE PROCEDURE update_courses_students(p_course_id IN courses_students.course_id%TYPE,
                                                    p_student_id IN courses_students.student_id%TYPE,
                                                    p_id IN courses_students.id%TYPE)
AS
BEGIN
    UPDATE courses_students
    SET course_id  = p_course_id,
        student_id = p_student_id
    where id = p_id;
end;
/

CREATE OR REPLACE PROCEDURE update_lecture(p_datetime IN lecture.dateTime%TYPE,
                                           p_room_id IN room.id%TYPE,
                                           p_id IN lecture.id%TYPE)
AS
BEGIN
    UPDATE lecture
    SET DATETIME = p_datetime,
        ROOM_ID  = p_room_id
    where id = p_id;
end;
/

CREATE OR REPLACE PROCEDURE update_professor(p_email IN professor.email%TYPE,
                                             p_firstname IN professor.firstName%TYPE,
                                             p_lastname IN professor.lastName%TYPE,
                                             p_password IN professor.password%TYPE,
                                             p_role IN professor.role%TYPE,
                                             p_id IN professor.id%TYPE)
AS
BEGIN
    UPDATE professor
    SET email     = p_email,
        firstName = p_firstname,
        lastName  = p_lastname,
        password  = p_password,
        role      = p_role
    where id = p_id;
end;
/

CREATE OR REPLACE PROCEDURE update_student(p_email IN student.email%TYPE,
                                           p_firstname IN student.firstName%TYPE,
                                           p_lastname IN student.lastName%TYPE,
                                           p_password IN student.password%TYPE,
                                           p_role IN student.role%TYPE,
                                           p_degree IN student.degree%TYPE,
                                           p_id IN student.id%TYPE)
AS
BEGIN
    UPDATE student
    SET email     = p_email,
        firstName = p_firstname,
        lastName  = p_lastname,
        password  = p_password,
        role      = p_role,
        degree    = p_degree
    where id = p_id;
end;
/

CREATE OR REPLACE PROCEDURE update_room(p_name IN room.name%TYPE,
                                        p_id IN room.id%TYPE)
AS
BEGIN
    UPDATE room
    SET name = p_name
    where id = p_id;
end;
/
