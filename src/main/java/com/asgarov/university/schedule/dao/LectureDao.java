package com.asgarov.university.schedule.dao;

import com.asgarov.university.schedule.dao.exception.DaoException;
import com.asgarov.university.schedule.domain.CourseLecture;
import com.asgarov.university.schedule.domain.Lecture;
import com.asgarov.university.schedule.domain.LectureView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class LectureDao extends AbstractDao<Long, Lecture> {

    private RoomDao roomDao;
    private CourseLectureDao courseLectureDao;

    @Autowired
    public void setRoomDao(final RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    @Autowired
    public void setCourseLectureDao(final CourseLectureDao courseLectureDao) {
        this.courseLectureDao = courseLectureDao;
    }

    @Override
    protected Lecture rowMapper(final ResultSet resultSet, final int rowNum) throws SQLException {
        Lecture lecture = new Lecture();
        lecture.setId(resultSet.getLong("id"));
        lecture.setDateTime(resultSet.getTimestamp("dateTime").toLocalDateTime());
        lecture.setRoom(roomDao.findById(resultSet.getLong("room_id")));
        return lecture;
    }

    public void deleteById(Long id) throws DaoException {
        courseLectureDao.deleteByLectureId(id);
        super.deleteById(id);
    }

    public List<Lecture> findAllByCourseId(final Long courseId) {
        List<CourseLecture> courseLectureList = courseLectureDao.findByCourseId(courseId);
        List<Lecture> requestedLectures = new ArrayList<>();
        courseLectureList.forEach(cl -> requestedLectures.add(findById(cl.getLectureId())));
        return requestedLectures;
    }

    @Override
    protected SqlParameterSource getParameterMap(Lecture lecture) {
        return new MapSqlParameterSource()
                .addValue("p_datetime", lecture.getDateTime())
                .addValue("p_room_id", lecture.getRoom().getId())
                .addValue("p_id", lecture.getId());
    }

    @Override
    protected String getCreateProcedureName() {
        return "create_lecture";
    }

    @Override
    protected String getUpdateProcedureName() {
        return "update_lecture";
    }

    @Override
    protected String getDeleteProcedureName() {
        return "delete_lecture";
    }

    @Override
    protected String getFindAllProcedure() {
        return "find_all_lectures";
    }

    @Override
    protected String getFindByIdProcedureName() {
        return "find_by_id_lecture";
    }

    @Override
    protected Lecture instantiateFromMap(Map<String, Object> result) {
        Lecture lecture = new Lecture();
        lecture.setId((Long) result.get("o_id"));

        Timestamp timestamp = (Timestamp) result.get("o_datetime");
        lecture.setDateTime(timestamp.toLocalDateTime());

        lecture.setRoom(roomDao.findById(((BigDecimal) result.get("o_room_id")).longValue()));
        return lecture;
    }

    public List<LectureView> findAllLectureView() {
        return (List) new SimpleJdbcCall(getJdbcTemplate())
                .withProcedureName("find_all_lecture_view")
                .returningResultSet("o_cursor", this::mapLectureView)
                .execute()
                .get("o_cursor");
    }

    public List<LectureView> findAllLecturesForStudent(Long studentId, LocalDate from, LocalDate to) {
        return findAllLectureViews(studentId, from, to, "find_all_lectures_for_student");
    }

    public List<LectureView> findAllLecturesForProfessor(Long professorId, LocalDate from, LocalDate to) {
        return findAllLectureViews(professorId, from, to, "find_all_lectures_for_professor");
    }

    public List<LectureView> findAllLectureViews(Long id, LocalDate from, LocalDate to, String procedureName) {
        return (List) new SimpleJdbcCall(getJdbcTemplate())
                .withProcedureName(procedureName)
                .returningResultSet("o_cursor", this::mapLectureView)
                .execute(new MapSqlParameterSource()
                        .addValue("p_id", id)
                        .addValue("p_datetime_from", from)
                        .addValue("p_datetime_until", to))
                .get("o_cursor");
    }

    protected LectureView mapLectureView(final ResultSet resultSet, final int rowNum) throws SQLException {
        LectureView lectureView = new LectureView();
        lectureView.setId(resultSet.getLong("id"));
        lectureView.setDateTime(resultSet.getTimestamp("datetime").toLocalDateTime());
        lectureView.setRoomName(resultSet.getString("room_name"));
        lectureView.setCourseName(resultSet.getString("course_name"));
        return lectureView;
    }
}
