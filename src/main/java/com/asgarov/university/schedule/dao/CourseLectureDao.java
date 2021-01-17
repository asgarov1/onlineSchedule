package com.asgarov.university.schedule.dao;

import com.asgarov.university.schedule.domain.CourseLecture;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class CourseLectureDao extends AbstractWithDeleteByCourseDao<Long, CourseLecture> {

    @Override
    protected CourseLecture rowMapper(final ResultSet resultSet, final int rowNum) throws SQLException {
        CourseLecture courseLecture = new CourseLecture();
        courseLecture.setId(resultSet.getLong("id"));
        courseLecture.setCourseId(resultSet.getLong("course_id"));
        courseLecture.setLectureId(resultSet.getLong("lecture_id"));
        return courseLecture;
    }

    public void deleteByLectureId(final Long lectureId) {
        new SimpleJdbcCall(getJdbcTemplate())
                .withProcedureName("delete_course_lectures_by_lecture_id")
                .execute(new MapSqlParameterSource().addValue("p_lecture_id", lectureId));
    }

    public List<CourseLecture> findByCourseId(final Long courseId) {
        return (List) new SimpleJdbcCall(getJdbcTemplate())
                .withProcedureName("find_by_course_id_course_lectures")
                .returningResultSet("o_cursor", this::rowMapper)
                .execute(new MapSqlParameterSource().addValue("p_course_id", courseId))
                .get("o_cursor");
    }

    @Override
    protected SqlParameterSource getParameterMap(CourseLecture object) {
        return new MapSqlParameterSource()
                .addValue("p_course_id", object.getCourseId())
                .addValue("p_lecture_id", object.getLectureId())
                .addValue("p_id", object.getId());
    }

    @Override
    protected String getCreateProcedureName() {
        return "create_course_lectures";
    }

    @Override
    protected String getUpdateProcedureName() {
        return "update_course_lectures";
    }

    @Override
    protected String getDeleteProcedureName() {
        return "delete_course_lectures";
    }

    @Override
    protected String getFindAllProcedure() {
        return "find_all_course_lectures";
    }

    @Override
    protected String getFindByIdProcedureName() {
        return "find_by_id_course_lectures";
    }

    @Override
    protected String getDeleteByCourseProcedureName() {
        return "delete_course_lectures_by_course_id";
    }

    @Override
    protected CourseLecture instantiateFromMap(Map<String, Object> result) {
        CourseLecture courseLecture = new CourseLecture();
        courseLecture.setId((Long) result.get("o_id"));
        courseLecture.setCourseId((Long) result.get("o_course_id"));
        courseLecture.setLectureId((Long) result.get("o_lecture_id"));

        return courseLecture;
    }
}
