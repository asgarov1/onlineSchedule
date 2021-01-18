ALTER SESSION SET CURRENT_SCHEMA = GUI;

-------------------
--CREATE PROCEDURES
-------------------


CREATE OR REPLACE PROCEDURE create_role(p_name IN role.NAME%TYPE,
                                        p_id IN role.ID%TYPE,
                                        o_id OUT role.ID%TYPE)
AS
BEGIN
    INSERT INTO role
    VALUES (p_id, p_name)
    returning id into o_id;
end;
/

CREATE OR REPLACE PROCEDURE create_degree(p_name IN degree.NAME%TYPE,
                                          p_id IN degree.ID%TYPE,
                                          o_id OUT degree.ID%TYPE)
AS
BEGIN
    INSERT INTO degree
    VALUES (p_id, p_name)
    returning id into o_id;
end;
/

CREATE OR REPLACE PROCEDURE create_course(p_name IN COURSE.NAME%TYPE,
                                          p_professor_id IN PROFESSOR.ID%TYPE,
                                          o_id OUT COURSE.ID%TYPE)
AS
BEGIN
    INSERT INTO COURSE (NAME, PROFESSOR_ID)
    VALUES (p_name, p_professor_id)
    returning id into o_id;
end;
/

CREATE OR REPLACE PROCEDURE create_course_lectures(p_course_id IN course_lectures.course_id%TYPE,
                                                   p_lecture_id IN course_lectures.lecture_id%TYPE,
                                                   o_id OUT course_lectures.id%TYPE)
AS
BEGIN
    INSERT INTO course_lectures (course_id, lecture_id)
    VALUES (p_course_id, p_lecture_id)
    returning id into o_id;
end;
/

CREATE OR REPLACE PROCEDURE create_courses_students(p_course_id IN courses_students.course_id%TYPE,
                                                    p_student_id IN courses_students.student_id%TYPE,
                                                    o_id OUT courses_students.id%TYPE)
AS
BEGIN
    INSERT INTO courses_students (course_id, student_id)
    VALUES (p_course_id, p_student_id)
    returning id into o_id;
end;
/

CREATE OR REPLACE PROCEDURE create_lecture(p_datetime IN lecture.dateTime%TYPE,
                                           p_room_id IN room.id%TYPE,
                                           o_id OUT lecture.id%TYPE)
AS
BEGIN
    INSERT INTO lecture (DATETIME, ROOM_ID)
    VALUES (p_datetime, p_room_id)
    returning id into o_id;
end;
/

CREATE OR REPLACE PROCEDURE create_person(p_email IN person.email%TYPE,
                                          p_firstName IN person.firstName%TYPE,
                                          p_lastName IN person.lastName%TYPE,
                                          p_password IN person.password%TYPE,
                                          p_role_id IN person.role_id%TYPE,
                                          o_id OUT person.email%TYPE)
AS
BEGIN
    INSERT INTO person (email, firstName, lastName, password, role_id)
    VALUES (p_email, p_firstName, p_lastName, p_password, p_role_id)
    returning email into o_id;
end;
/

CREATE OR REPLACE PROCEDURE create_professor(p_person_id IN person.email%TYPE,
                                             o_id OUT professor.id%TYPE)
AS
BEGIN
    INSERT INTO professor (person_id)
    VALUES (p_person_id)
    returning id into o_id;
end;
/

CREATE OR REPLACE PROCEDURE create_student(p_person_id IN person.email%TYPE,
                                           p_degree IN student.degree_id%TYPE,
                                           o_id OUT student.id%TYPE)
AS
BEGIN
    INSERT INTO student (person_id, degree_id)
    VALUES (p_person_id, p_degree)
    returning id into o_id;
end;
/

CREATE OR REPLACE PROCEDURE create_room(p_name IN room.name%TYPE,
                                        o_id OUT room.id%TYPE)
AS
BEGIN
    INSERT INTO room (name)
    VALUES (p_name)
    returning id into o_id;
end;
/

---------------------
--- UPDATE PROCEDURES
---------------------

CREATE OR REPLACE PROCEDURE update_role(p_name IN role.NAME%TYPE,
                                        p_id IN role.ID%TYPE)
AS
BEGIN
    UPDATE role
    SET NAME = p_name
    where ID = p_id;
end;
/

CREATE OR REPLACE PROCEDURE update_degree(p_name IN degree.NAME%TYPE,
                                          p_id IN degree.ID%TYPE)
AS
BEGIN
    UPDATE degree
    SET NAME = p_name
    where ID = p_id;
end;
/

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

CREATE OR REPLACE PROCEDURE update_person(p_firstname IN person.firstName%TYPE,
                                          p_lastname IN person.lastName%TYPE,
                                          p_password IN person.password%TYPE,
                                          p_role_id IN person.role_id%TYPE,
                                          p_id IN person.email%TYPE)
AS
BEGIN
    UPDATE person
    SET firstName = p_firstname,
        lastName  = p_lastname,
        password  = p_password,
        role_id = p_role_id
    where email = p_id;
end;
/

CREATE OR REPLACE PROCEDURE update_professor(p_person_id IN person.email%TYPE,
                                             p_id IN professor.id%TYPE)
AS
BEGIN
    UPDATE professor
    SET person_id = p_person_id
    where id = p_id;
end;
/

CREATE OR REPLACE PROCEDURE update_student(p_person_id IN person.email%TYPE,
                                           p_degree IN student.degree_id%TYPE,
                                           p_id IN student.id%TYPE)
AS
BEGIN
    UPDATE student
    SET person_id = p_person_id,
        degree_id = p_degree
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

---------------------
--- DELETE PROCEDURES
---------------------
CREATE OR REPLACE PROCEDURE delete_role(p_id IN role.ID%TYPE)
AS
BEGIN
    DELETE role where ID = p_id;
end;
/

CREATE OR REPLACE PROCEDURE delete_degree(p_id IN degree.ID%TYPE)
AS
BEGIN
    DELETE degree where ID = p_id;
end;
/

CREATE OR REPLACE PROCEDURE delete_course(p_id IN COURSE.ID%TYPE)
AS
BEGIN
    DELETE COURSE where ID = p_id;
end;
/

CREATE OR REPLACE PROCEDURE delete_course_lectures(p_id IN course_lectures.id%TYPE)
AS
BEGIN
    DELETE course_lectures where id = p_id;
end;
/

CREATE OR REPLACE PROCEDURE delete_courses_students(p_id IN courses_students.id%TYPE)
AS
BEGIN
    DELETE courses_students where id = p_id;
end;
/

CREATE OR REPLACE PROCEDURE delete_lecture(p_id IN lecture.id%TYPE)
AS
BEGIN
    DELETE lecture where id = p_id;
end;
/

CREATE OR REPLACE PROCEDURE delete_person(p_id IN person.email%TYPE)
AS
BEGIN
    DELETE person where email = p_id;
end;
/

CREATE OR REPLACE PROCEDURE delete_professor(p_id IN professor.id%TYPE)
AS
BEGIN
    DELETE professor where id = p_id;
end;
/

CREATE OR REPLACE PROCEDURE delete_student(p_id IN student.id%TYPE)
AS
BEGIN
    DELETE student where id = p_id;
end;
/

CREATE OR REPLACE PROCEDURE delete_room(p_id IN room.id%TYPE)
AS
BEGIN
    DELETE room where id = p_id;
end;
/

CREATE OR REPLACE PROCEDURE delete_course_lectures_by_course_id(p_course_id IN course_lectures.id%TYPE)
AS
BEGIN
    DELETE course_lectures where course_id = p_course_id;
end;
/


CREATE OR REPLACE PROCEDURE delete_course_lectures_by_lecture_id(p_lecture_id IN lecture.id%TYPE)
AS
BEGIN
    DELETE course_lectures where lecture_id = p_lecture_id;
end;
/

CREATE OR REPLACE PROCEDURE delete_courses_students_by_course_id(p_course_id IN course_lectures.id%TYPE)
AS
BEGIN
    DELETE courses_students where course_id = p_course_id;
end;
/

CREATE OR REPLACE PROCEDURE unregister_student(p_course_id IN course.id%TYPE,
                                               p_student_id IN student.id%TYPE)
AS
BEGIN
    DELETE courses_students where course_id = p_course_id and student_id = p_student_id;
end;
/

CREATE OR REPLACE PROCEDURE delete_courses_students_by_student_id(p_student_id IN student.id%TYPE)
AS
BEGIN
    DELETE courses_students where student_id = p_student_id;
end;
/

-----------------------
--- FIND ALL PROCEDURES
-----------------------

CREATE OR REPLACE PROCEDURE find_all_roles(o_cursor OUT SYS_REFCURSOR)
AS
BEGIN
    OPEN o_cursor FOR
        SELECT * FROM role;
END;
/

CREATE OR REPLACE PROCEDURE find_all_degrees(o_cursor OUT SYS_REFCURSOR)
AS
BEGIN
    OPEN o_cursor FOR
        SELECT * FROM degree;
END;
/

CREATE OR REPLACE PROCEDURE find_all_courses(o_cursor OUT SYS_REFCURSOR)
AS
BEGIN
    OPEN o_cursor FOR
        SELECT * FROM course;
END;
/

CREATE OR REPLACE PROCEDURE find_all_course_lectures(o_cursor OUT SYS_REFCURSOR)
AS
BEGIN
    OPEN o_cursor FOR
        SELECT * FROM course_lectures;
END;
/

CREATE OR REPLACE PROCEDURE find_all_courses_students(o_cursor OUT SYS_REFCURSOR)
AS
BEGIN
    OPEN o_cursor FOR
        SELECT * FROM courses_students;
END;
/

CREATE OR REPLACE PROCEDURE find_all_lectures(o_cursor OUT SYS_REFCURSOR)
AS
BEGIN
    OPEN o_cursor FOR
        SELECT * FROM lecture;
END;
/

CREATE OR REPLACE PROCEDURE find_all_persons(o_cursor OUT SYS_REFCURSOR)
AS
BEGIN
    OPEN o_cursor FOR
        SELECT * FROM person;
END;
/

CREATE OR REPLACE PROCEDURE find_all_professors(o_cursor OUT SYS_REFCURSOR)
AS
BEGIN
    OPEN o_cursor FOR
        SELECT * FROM professor;
END;
/

CREATE OR REPLACE PROCEDURE find_all_students(o_cursor OUT SYS_REFCURSOR)
AS
BEGIN
    OPEN o_cursor FOR
        SELECT * FROM student;
END;
/

CREATE OR REPLACE PROCEDURE find_all_rooms(o_cursor OUT SYS_REFCURSOR)
AS
BEGIN
    OPEN o_cursor FOR
        SELECT * FROM room;
END;
/



-------------------------
--- Find by Id Procedures
-------------------------

CREATE OR REPLACE PROCEDURE find_by_id_role(p_id IN role.ID%TYPE,
                                            o_name OUT role.NAME%TYPE)
AS
BEGIN
    SELECT name
    INTO o_name
    from role
    WHERE ID = p_id;
END;
/

CREATE OR REPLACE PROCEDURE find_by_id_degree(p_id IN degree.ID%TYPE,
                                              o_name OUT degree.NAME%TYPE)
AS
BEGIN
    SELECT name
    INTO o_name
    from degree
    WHERE ID = p_id;
END;
/

CREATE OR REPLACE PROCEDURE find_by_id_course(p_id IN course.ID%TYPE,
                                              o_name OUT course.NAME%TYPE,
                                              o_professor_id OUT course.professor_id%TYPE)
AS
BEGIN
    SELECT name, professor_id
    INTO o_name, o_professor_id
    from course
    WHERE ID = p_id;
END;
/

CREATE OR REPLACE PROCEDURE find_by_id_course_lectures(p_id IN course_lectures.ID%TYPE,
                                                       o_course_id OUT course_lectures.COURSE_ID%TYPE,
                                                       o_lecture_id OUT course_lectures.LECTURE_ID%TYPE)
AS
BEGIN
    SELECT course_id, lecture_id
    INTO o_course_id, o_lecture_id
    from course_lectures
    WHERE ID = p_id;
END;
/

CREATE OR REPLACE PROCEDURE find_by_id_courses_students(p_id IN courses_students.ID%TYPE,
                                                        o_course_id OUT courses_students.COURSE_ID%TYPE,
                                                        o_student_id OUT courses_students.STUDENT_ID%TYPE)
AS
BEGIN
    SELECT course_id, student_id
    INTO o_course_id, o_student_id
    from courses_students
    WHERE ID = p_id;
END;
/


CREATE OR REPLACE PROCEDURE find_by_id_lecture(p_id IN lecture.ID%TYPE,
                                               o_datetime OUT lecture.dateTime%TYPE,
                                               o_room_id OUT lecture.room_id%TYPE)
AS
BEGIN
    SELECT dateTime, room_id
    INTO o_datetime, o_room_id
    from lecture
    WHERE ID = p_id;
END;
/


CREATE OR REPLACE PROCEDURE find_by_id_person(p_id IN person.email%TYPE,
                                              o_email OUT person.email%TYPE,
                                              o_firstname OUT person.firstName%TYPE,
                                              o_lastname OUT person.lastName%TYPE,
                                              o_password OUT person.password%TYPE,
                                              o_role_id OUT person.role_id%TYPE)
AS
BEGIN
    SELECT email, firstName, lastName, password, role_id
    INTO o_email, o_firstname, o_lastname, o_password, o_role_id
    from person
    WHERE email = p_id;
END;
/

CREATE OR REPLACE PROCEDURE find_by_id_professor(p_id IN professor.id%TYPE,
                                                 o_person_id OUT professor.person_id%TYPE)
AS
BEGIN
    SELECT person_id
    INTO o_person_id
    from professor
    WHERE ID = p_id;
END;
/


CREATE OR REPLACE PROCEDURE find_by_id_room(p_id IN room.ID%TYPE,
                                            o_name OUT room.name%TYPE)
AS
BEGIN
    SELECT name
    INTO o_name
    from room
    WHERE ID = p_id;
END;
/


CREATE OR REPLACE PROCEDURE find_by_id_student(p_id IN student.id%TYPE,
                                               o_person_id OUT student.person_id%TYPE,
                                               o_degree OUT student.degree_id%TYPE
)
AS
BEGIN
    SELECT person_id, degree_id
    INTO o_person_id, o_degree
    from student
    WHERE ID = p_id;
END;
/

CREATE OR REPLACE PROCEDURE find_by_lecture_id_course_lectures(p_course_id IN course.ID%TYPE,
                                                               o_cursor OUT SYS_REFCURSOR)
AS
BEGIN
    OPEN o_cursor FOR
        SELECT * FROM course_lectures WHERE course_id = p_course_id;
END;
/

CREATE OR REPLACE PROCEDURE find_by_course_id_courses_students(p_course_id IN course.ID%TYPE,
                                                               o_cursor OUT SYS_REFCURSOR)
AS
BEGIN
    OPEN o_cursor FOR
        SELECT * FROM courses_students WHERE course_id = p_course_id;
END;
/

CREATE OR REPLACE PROCEDURE find_by_course_id_course_lectures(p_course_id IN course.ID%TYPE,
                                                              o_cursor OUT SYS_REFCURSOR)
AS
BEGIN
    OPEN o_cursor FOR
        SELECT * FROM course_lectures WHERE course_id = p_course_id;
END;
/

CREATE OR REPLACE PROCEDURE find_all_lecture_view(o_cursor OUT SYS_REFCURSOR)
AS
BEGIN
    OPEN o_cursor FOR
        SELECT * FROM lecture_view;
END;
/

CREATE OR REPLACE PROCEDURE find_all_lectures_for_student(p_id IN student.ID%TYPE,
                                                          p_datetime_from IN lecture.DATETIME%TYPE,
                                                          p_datetime_until IN lecture.DATETIME%TYPE,
                                                          o_cursor OUT SYS_REFCURSOR)
AS
BEGIN
    OPEN o_cursor FOR
        SELECT id,
               datetime,
               room_name,
               course_name
        FROM schedule_view s
        where s.STUDENT_ID = p_id
          and datetime >= p_datetime_from
          and datetime <= p_datetime_until;
END;
/

CREATE OR REPLACE PROCEDURE find_all_lectures_for_professor(p_id IN professor.ID%TYPE,
                                                            p_datetime_from IN lecture.DATETIME%TYPE,
                                                            p_datetime_until IN lecture.DATETIME%TYPE,
                                                            o_cursor OUT SYS_REFCURSOR)
AS
BEGIN
    OPEN o_cursor FOR
        SELECT id,
               datetime,
               room_name,
               course_name
        FROM schedule_view s
        where s.PROFESSOR_ID = p_id
          and datetime >= p_datetime_from
          and datetime <= p_datetime_until;
END;
/