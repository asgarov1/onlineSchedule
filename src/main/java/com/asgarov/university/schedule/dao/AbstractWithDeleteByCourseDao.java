package com.asgarov.university.schedule.dao;

import com.asgarov.university.schedule.dao.exception.DaoException;

public abstract class AbstractWithDeleteByCourseDao<K, T> extends AbstractDao<K, T> {
    public void deleteByCourseId(final K id) {
        getJdbcTemplate().update(getDeleteByCourseQuery(), id);
    }

    private String getDeleteByCourseQuery() {
        return "delete from " + tableName() + " where course_id = ?";
    }
}
