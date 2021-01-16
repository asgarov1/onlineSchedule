CREATE OR REPLACE PROCEDURE create_course(
        p_name IN COURSE.NAME%TYPE,
        p_professor_id IN PROFESSOR.ID%TYPE)
    AS
    BEGIN

        INSERT INTO COURSE (NAME, PROFESSOR_ID) VALUES (p_name, p_professor_id);
END;
/

CREATE OR REPLACE PROCEDURE create_course_lecture(
    p_course_id IN course_lectures.course_id%TYPE,
    p_lecture_id IN course_lectures.lecture_id%TYPE,
    p_id OUT course_lectures.id%TYPE
)
AS
BEGIN
    INSERT INTO course_lectures (course_id, lecture_id) VALUES (p_course_id, p_lecture_id)
    returning id into p_id;
end;
/

CREATE OR REPLACE PROCEDURE create_courses_students(
    p_course_id IN courses_students.course_id%TYPE,
    p_student_id IN courses_students.student_id%TYPE,
    p_id OUT courses_students.id%TYPE
)
AS
BEGIN
    INSERT INTO courses_students (course_id, student_id) VALUES (p_course_id, p_student_id)
    returning id into p_id;
end;
/

CREATE OR REPLACE PROCEDURE create_lecture(
    p_datetime IN lecture.dateTime%TYPE,
    p_room_id IN room.id%TYPE,
    p_id OUT lecture.id%TYPE
)
AS
BEGIN
    INSERT INTO lecture (DATETIME, ROOM_ID) VALUES (p_datetime, p_room_id)
    returning id into p_id;
end;
/

CREATE OR REPLACE PROCEDURE create_professor(
    p_email IN professor.email%TYPE,
    p_firstname IN professor.firstName%TYPE,
    p_lastname IN professor.lastName%TYPE,
    p_password IN professor.password%TYPE,
    p_role IN professor.role%TYPE,
    p_id OUT professor.id%TYPE
)
AS
BEGIN
    INSERT INTO professor (email, firstName, lastName, password, role)
    VALUES (p_email, p_firstname, p_lastname, p_password, p_role)
    returning id into p_id;
end;
/

CREATE OR REPLACE PROCEDURE create_student(
    p_email IN student.email%TYPE,
    p_firstname IN student.firstName%TYPE,
    p_lastname IN student.lastName%TYPE,
    p_password IN student.password%TYPE,
    p_role IN student.role%TYPE,
    p_degree IN student.degree%TYPE,
    p_id OUT student.id%TYPE
)
AS
BEGIN
    INSERT INTO student (email, firstName, lastName, password, role, degree)
    VALUES (p_email, p_firstname, p_lastname, p_password, p_role, p_degree)
    returning id into p_id;
end;
/

CREATE OR REPLACE PROCEDURE create_room(
    p_name IN room.name%TYPE,
    p_id OUT room.id%TYPE
)
AS
BEGIN
    INSERT INTO room (name) VALUES (p_name)
    returning id into p_id;
end;
/