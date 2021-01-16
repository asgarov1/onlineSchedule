package com.asgarov.university.schedule.dao;

import com.asgarov.university.schedule.domain.CourseLecture;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CourseLectureDao extends AbstractWithDeleteByCourseDao<Long, CourseLecture> {

    private String getFindByCourseIdQuery(Long courseId) {
        return "select * from " + tableName() + " where course_id = " + courseId + "";
    }

    private String getFindByLectureIdQuery(Long lectureId) {
        return "select * from " + tableName() + " where lecture_id = " + lectureId + "";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE " + tableName() + " SET course_id = ?, lecture_id = ? WHERE id = ?";
    }

    @Override
    protected CourseLecture rowMapper(final ResultSet resultSet, final int rowNum) throws SQLException {
        CourseLecture courseLecture = new CourseLecture();
        courseLecture.setId(resultSet.getLong("id"));
        courseLecture.setCourseId(resultSet.getLong("course_id"));
        courseLecture.setLectureId(resultSet.getLong("lecture_id"));
        return courseLecture;
    }

    @Override
    protected Object[] updateParameters(final CourseLecture courseLecture) {
        return new Object[] {
                courseLecture.getCourseId(), courseLecture.getLectureId(), courseLecture.getId()
        };
    }

    @Override
    protected String tableName() {
        return "course_lectures";
    }

    public void deleteByLectureId(final Long lectureId) {
        getJdbcTemplate().update(getDeleteByLectureQuery(), lectureId);
    }

    private String getDeleteByLectureQuery() {
        return "delete from " + tableName() + " where lecture_id = ?";
    }

    public List<CourseLecture> findByCourseId(final Long courseId) {
        return getJdbcTemplate().query(getFindByCourseIdQuery(courseId), this::rowMapper);
    }

    public List<CourseLecture> findByLectureId(final Long courseId) {
        return getJdbcTemplate().query(getFindByLectureIdQuery(courseId), this::rowMapper);
    }

    @Override
    protected SqlParameterSource getParameterMap(CourseLecture object) {
        return new MapSqlParameterSource()
                .addValue("p_course_id", object.getCourseId())
                .addValue("p_lecture_id", object.getLectureId());
    }

    @Override
    protected String getCreateProcedureName() {
        return "create_course_lectures";
    }
}
