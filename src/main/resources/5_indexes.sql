ALTER SESSION SET CURRENT_SCHEMA = GUI;

CREATE INDEX course_id_in_course_lectures
ON course_lectures (course_id);

CREATE INDEX course_id_in_courses_students
ON courses_students (course_id);