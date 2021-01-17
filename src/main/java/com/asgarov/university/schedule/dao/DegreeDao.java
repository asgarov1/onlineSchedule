package com.asgarov.university.schedule.dao;

import com.asgarov.university.schedule.domain.Role;
import com.asgarov.university.schedule.domain.Student;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Repository
public class DegreeDao extends AbstractDao<Long, Student.Degree> {

    @Override
    protected String getFindAllProcedure() {
        return "find_all_degrees";
    }

    @Override
    protected String getCreateProcedureName() {
        return "create_degree";
    }

    @Override
    protected String getUpdateProcedureName() {
        return "update_degree";
    }

    @Override
    protected String getDeleteProcedureName() {
        return "delete_degree";
    }

    @Override
    protected String getFindByIdProcedureName() {
        return "find_by_id_degree";
    }

    @Override
    protected SqlParameterSource getParameterMap(Student.Degree degree) {
        return new MapSqlParameterSource()
                .addValue("p_name", degree.name())
                .addValue("p_id", degree.ordinal());

    }

    @Override
    protected Student.Degree rowMapper(ResultSet resultSet, int rowNum) throws SQLException {
        return Student.Degree.valueOf(resultSet.getString("name"));
    }

    @Override
    protected Student.Degree instantiateFromMap(Map<String, Object> result) {
        return Student.Degree.valueOf((String) result.get("o_name"));
    }
}
