package com.asgarov.university.schedule.dao;

import com.asgarov.university.schedule.dao.exception.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public abstract class AbstractDao<K, T> {

    private JdbcTemplate jdbcTemplate;

    protected abstract T rowMapper(final ResultSet resultSet, final int rowNum) throws SQLException;

    protected abstract String tableName();

    protected String getFindByIdQuery(K id) {
        return "select * from " + tableName() + " where id = " + id + "";
    }

    protected String getFindAllQuery() {
        return "select * from " + tableName() + "";
    }

    public Long create(T object) {
        Map<String, Object> execute = new SimpleJdbcCall(getJdbcTemplate())
                .withProcedureName(getCreateProcedureName())
                .execute(getParameterMap(object));
        return ((BigDecimal) execute.get("P_ID")).longValue();
    }

    protected abstract SqlParameterSource getParameterMap(T object);

    protected abstract String getCreateProcedureName();

    protected abstract String getUpdateProcedureName();

    protected abstract String getDeleteProcedureName();

    public T findById(K id) {
        return jdbcTemplate.queryForObject(getFindByIdQuery(id), this::rowMapper);
    }

    public void update(T object) {
        new SimpleJdbcCall(getJdbcTemplate())
                .withProcedureName(getUpdateProcedureName())
                .execute(getParameterMap(object));
    }

    public void deleteById(K id) throws DaoException {
        new SimpleJdbcCall(getJdbcTemplate())
                .withProcedureName(getDeleteProcedureName())
                .execute(new MapSqlParameterSource().addValue("p_id", id));
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
