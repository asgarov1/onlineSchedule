package com.asgarov.university.schedule.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

public abstract class AbstractWithDeleteByCourseDao<K, T> extends AbstractDao<K, T> {
    protected abstract String getDeleteByCourseProcedureName();

    public void deleteByCourseId(final K id) {
        new SimpleJdbcCall(getJdbcTemplate())
                .withProcedureName(getDeleteByCourseProcedureName())
                .execute(new MapSqlParameterSource().addValue("p_course_id", id));
    }
}
