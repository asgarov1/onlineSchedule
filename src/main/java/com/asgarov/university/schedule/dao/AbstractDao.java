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

    public static final String ID_PARAMETER = "p_id";
    private JdbcTemplate jdbcTemplate;

    protected abstract T rowMapper(final ResultSet resultSet, final int rowNum) throws SQLException;

    protected abstract String tableName();

    public Long create(T object) {
        Map<String, Object> execute = new SimpleJdbcCall(getJdbcTemplate())
                .withProcedureName(getCreateProcedureName())
                .execute(getParameterMap(object));
        return ((BigDecimal) execute.get(ID_PARAMETER)).longValue();
    }

    protected abstract SqlParameterSource getParameterMap(T object);

    protected abstract String getCreateProcedureName();

    protected abstract String getUpdateProcedureName();

    protected abstract String getDeleteProcedureName();

    protected abstract String getFindByIdProcedureName();


    public T findById(K id) {
        Map<String, Object> result = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName(getFindByIdProcedureName())
                .execute(new MapSqlParameterSource().addValue(ID_PARAMETER, id));
        result.put("p_id", id);
        return instantiateFromMap(result);
    }

    protected abstract T instantiateFromMap(Map<String, Object> result);


    public void update(T object) {
        new SimpleJdbcCall(getJdbcTemplate())
                .withProcedureName(getUpdateProcedureName())
                .execute(getParameterMap(object));
    }

    public void deleteById(K id) throws DaoException {
        new SimpleJdbcCall(getJdbcTemplate())
                .withProcedureName(getDeleteProcedureName())
                .execute(new MapSqlParameterSource().addValue(ID_PARAMETER, id));
    }

    public List<T> findAll() {
        return (List) new SimpleJdbcCall(getJdbcTemplate())
                .withProcedureName(getFindAllProcedure())
                .returningResultSet("o_cursor", this::rowMapper)
                .execute()
                .get("o_cursor");
    }

    protected abstract String getFindAllProcedure();

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Autowired
    public void setJdbcTemplate(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        jdbcTemplate.setResultsMapCaseInsensitive(true);
    }

}
