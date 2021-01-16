package com.asgarov.university.schedule.dao;

import com.asgarov.university.schedule.domain.CourseLecture;
import com.asgarov.university.schedule.domain.CourseStudent;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CourseStudentDao extends AbstractWithDeleteByCourseDao<Long, CourseStudent> {

    @Override
    protected String getUpdateQuery() {
        return "UPDATE " + tableName() + " SET course_id = ?, student_id = ? WHERE id = ?";
    }

    @Override
    protected CourseStudent rowMapper(final ResultSet resultSet, final int rowNum) throws SQLException {
        CourseStudent courseStudent = new CourseStudent();
        courseStudent.setId(resultSet.getLong("id"));
        courseStudent.setCourseId(resultSet.getLong("course_id"));
        courseStudent.setStudentId(resultSet.getLong("student_id"));
        return courseStudent;
    }

    @Override
    protected Object[] updateParameters(final CourseStudent courseStudent) {
        return new Object[] { courseStudent.getCourseId(), courseStudent.getStudentId(), courseStudent.getId() };
    }

    @Override
    protected String tableName() {
        return "courses_students";
    }

    public List<CourseStudent> findAllByCourseId(final Long courseId) {
        return getJdbcTemplate().query(getFindByCourseIdQuery(courseId), this::rowMapper);
    }

    public void deleteByStudentId(final Long id) {
        getJdbcTemplate().update(getDeleteByStudentQuery(), id);
    }

    private String getDeleteByStudentQuery() {
        return "delete from " + tableName() + " where student_id = ?";
    }

    private String getFindByCourseIdQuery(Long courseId) {
        return "select * from " + tableName() + " where course_id = " + courseId + "";
    }

    @Override
    protected SqlParameterSource getParameterMap(CourseStudent object) {
        return new MapSqlParameterSource()
                .addValue("p_course_id", object.getCourseId())
                .addValue("p_student_id", object.getStudentId());
    }

    @Override
    protected String getCreateProcedureName() {
        return "create_courses_students";
    }
}
