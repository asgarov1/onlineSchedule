package com.asgarov.university.schedule.dao;


import com.asgarov.university.schedule.domain.Professor;
import com.asgarov.university.schedule.domain.Role;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Repository
public class ProfessorDao extends AbstractDao<Long, Professor> {

    @Override
    protected Professor rowMapper(final ResultSet resultSet, final int rowNum) throws SQLException {
        Professor professor = new Professor();
        professor.setId(resultSet.getLong("id"));
        professor.setEmail(resultSet.getString("email"));
        professor.setFirstName(resultSet.getString("firstName"));
        professor.setLastName(resultSet.getString("lastName"));
        professor.setPassword(resultSet.getString("password"));
        professor.setRole(Role.valueOf(resultSet.getString("role")));
        return professor;
    }

    @Override
    protected SqlParameterSource getParameterMap(Professor professor) {
        return new MapSqlParameterSource()
                .addValue("p_email", professor.getEmail())
                .addValue("p_firstname", professor.getFirstName())
                .addValue("p_lastname", professor.getLastName())
                .addValue("p_password", professor.getPassword())
                .addValue("p_role", professor.getRole())
                .addValue("p_id", professor.getId());
    }

    @Override
    protected String getCreateProcedureName() {
        return "create_professor";
    }

    @Override
    protected String getUpdateProcedureName() {
        return "update_professor";
    }

    @Override
    protected String getDeleteProcedureName() {
        return "delete_professor";
    }

    @Override
    protected String getFindAllProcedure() {
        return "find_all_professors";
    }

    @Override
    protected String getFindByIdProcedureName() {
        return "find_by_id_professor";
    }

    @Override
    protected Professor instantiateFromMap(Map<String, Object> result) {
        Professor professor = new Professor();

        professor.setId((Long) result.get("p_id"));
        professor.setEmail((String) result.get("o_email"));
        professor.setFirstName((String) result.get("o_firstname"));
        professor.setLastName((String) result.get("o_lastname"));
        professor.setPassword((String) result.get("o_password"));
        professor.setRole(Role.valueOf((String) result.get("o_role")));

        return professor;
    }
}
