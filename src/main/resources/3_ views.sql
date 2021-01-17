---------
--- VIEWS
---------
CREATE OR REPLACE VIEW lecture_view AS
SELECT L.id,
       L.datetime,
       R.NAME as room_name,
       C.NAME as course_name
FROM lecture L
         join COURSE_LECTURES CL on L.ID = CL.LECTURE_ID
         join COURSE C on C.ID = CL.COURSE_ID
         join ROOM R on R.ID = L.ROOM_ID;



CREATE OR REPLACE VIEW schedule_view AS
SELECT L.id,
       L.datetime,
       R.NAME as room_name,
       C.NAME as course_name,
       CS.STUDENT_ID,
       C.PROFESSOR_ID
FROM lecture L
         join COURSE_LECTURES CL on L.ID = CL.LECTURE_ID
         join COURSE C on C.ID = CL.COURSE_ID
         join COURSES_STUDENTS CS on C.ID = CS.COURSE_ID
         join ROOM R on R.ID = L.ROOM_ID;