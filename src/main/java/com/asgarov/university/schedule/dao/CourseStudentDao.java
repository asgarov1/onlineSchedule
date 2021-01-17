package com.asgarov.university.schedule.dao;

import com.asgarov.university.schedule.domain.CourseStudent;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class CourseStudentDao extends AbstractWithDeleteByCourseDao<Long, CourseStudent> {

    @Override
    protected CourseStudent rowMapper(final ResultSet resultSet, final int rowNum) throws SQLException {
        CourseStudent courseStudent = new CourseStudent();
        courseStudent.setId(resultSet.getLong("id"));
        courseStudent.setCourseId(resultSet.getLong("course_id"));
        courseStudent.setStudentId(resultSet.getLong("student_id"));
        return courseStudent;
    }

    public List<CourseStudent> findAllByCourseId(final Long courseId) {
        return (List) new SimpleJdbcCall(getJdbcTemplate())
                .withProcedureName("find_by_course_id_courses_students")
                .returningResultSet("o_cursor", this::rowMapper)
                .execute(new MapSqlParameterSource().addValue("p_course_id", courseId))
                .get("o_cursor");
    }

    public void deleteByStudentId(final Long studentId) {
        new SimpleJdbcCall(getJdbcTemplate())
                .withProcedureName("delete_courses_students_by_student_id")
                .execute(new MapSqlParameterSource().addValue("p_student_id", studentId));
    }

    @Override
    protected SqlParameterSource getParameterMap(CourseStudent object) {
        return new MapSqlParameterSource()
                .addValue("p_course_id", object.getCourseId())
                .addValue("p_student_id", object.getStudentId())
                .addValue("p_id", object.getId());
    }

    @Override
    protected String getCreateProcedureName() {
        return "create_courses_students";
    }

    @Override
    protected String getUpdateProcedureName() {
        return "update_courses_students";
    }

    @Override
    protected String getDeleteProcedureName() {
        return "delete_courses_students";
    }

    @Override
    protected String getFindAllProcedure() {
        return "find_all_courses_students";
    }

    @Override
    protected String getFindByIdProcedureName() {
        return "find_by_id_courses_students";
    }

    @Override
    protected String getDeleteByCourseProcedureName() {
        return "delete_courses_students_by_course_id";
    }

    @Override
    protected CourseStudent instantiateFromMap(Map<String, Object> result) {
        CourseStudent courseStudent = new CourseStudent();
        courseStudent.setId((Long) result.get("o_id"));
        courseStudent.setCourseId((Long) result.get("o_course_id"));
        courseStudent.setStudentId((Long) result.get("o_student_id"));

        return courseStudent;
    }

    public void delete(Long course_id, Long studentId) {
        new SimpleJdbcCall(getJdbcTemplate())
                .withProcedureName("unregister_student")
                .execute(new MapSqlParameterSource().addValue("p_course_id", course_id)
                        .addValue("p_student_id", studentId));
    }
}
