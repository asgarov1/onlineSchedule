package com.asgarov.university.schedule.dao;

import com.asgarov.university.schedule.dao.exception.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public abstract class AbstractDao<K, T> {

    private JdbcTemplate jdbcTemplate;

    protected abstract String getUpdateQuery();

    protected abstract T rowMapper(final ResultSet resultSet, final int rowNum) throws SQLException;

    protected abstract Object[] updateParameters(T object);

    protected abstract String tableName();

    protected String getFindByIdQuery(K id) {
        return "select * from " + tableName() + " where id = " + id + "";
    }

    protected String getDeleteQuery() {
        return "delete from " + tableName() + " where id = ?";
    }

    protected String getFindAllQuery() {
        return "select * from " + tableName() + "";
    }

    public Long create(T object) {
        return (Long) new SimpleJdbcCall(getJdbcTemplate())
                .withProcedureName(getCreateProcedureName())
                .execute(getParameterMap(object))
                .get("p_id");
    }

    protected abstract SqlParameterSource getParameterMap(T object);

    protected abstract String getCreateProcedureName();

    public T findById(K id) {
        return jdbcTemplate.queryForObject(getFindByIdQuery(id), this::rowMapper);
    }

    public void update(T object) throws DaoException {
        if (jdbcTemplate.update(getUpdateQuery(), updateParameters(object)) == 0) {
            throw new DaoException("Problem updating entity");
        }
    }

    public void deleteById(K id) throws DaoException {
        jdbcTemplate.update(getDeleteQuery(), id);
    }

    public List<T> findAll() {
        return jdbcTemplate.query(getFindAllQuery(), this::rowMapper);
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Autowired
    public void setJdbcTemplate(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}
