package com.asgarov.university.schedule.dao;

import com.asgarov.university.schedule.dao.exception.DaoException;
import com.asgarov.university.schedule.domain.CourseLecture;
import com.asgarov.university.schedule.domain.Lecture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
    protected String getUpdateQuery() {
        return "UPDATE " + tableName() + " SET dateTime = ?, room_id = ? WHERE id = ?";
    }

    @Override
    protected Lecture rowMapper(final ResultSet resultSet, final int rowNum) throws SQLException {
        Lecture lecture = new Lecture();
        lecture.setId(resultSet.getLong("id"));
        lecture.setDateTime(resultSet.getTimestamp("dateTime").toLocalDateTime());
        lecture.setRoom(roomDao.findById(resultSet.getLong("room_id")));
        return lecture;
    }

    @Override
    protected Object[] updateParameters(final Lecture lecture) {
        return new Object[] { lecture.getDateTime(), lecture.getRoom().getId(), lecture.getId() };
    }

    @Override
    protected String tableName() {
        return "lecture";
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

    public List<Lecture> findAmount(int amount) {
        return getJdbcTemplate().query(getFindAmountQuery(amount), this::rowMapper);
    }

    private String getFindAmountQuery(int amount) {
        return "select * from " + tableName() + " limit " + amount + "";
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
}
