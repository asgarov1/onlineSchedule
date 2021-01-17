package com.asgarov.university.schedule.dao;


import com.asgarov.university.schedule.domain.Professor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Repository
public class ProfessorDao extends AbstractDao<Long, Professor> {

    private final RoleDao roleDao;

    public ProfessorDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    protected Professor rowMapper(final ResultSet resultSet, final int rowNum) throws SQLException {
        Professor professor = new Professor();
        professor.setId(resultSet.getLong("id"));
        professor.setEmail(resultSet.getString("email"));
        professor.setFirstName(resultSet.getString("firstName"));
        professor.setLastName(resultSet.getString("lastName"));
        professor.setPassword(resultSet.getString("password"));
        professor.setRole(roleDao.findById(resultSet.getLong("role_id")));
        return professor;
    }

    @Override
    protected SqlParameterSource getParameterMap(Professor professor) {
        return new MapSqlParameterSource()
                .addValue("p_email", professor.getEmail())
                .addValue("p_firstname", professor.getFirstName())
                .addValue("p_lastname", professor.getLastName())
                .addValue("p_password", professor.getPassword())
                .addValue("p_role", professor.getRole().ordinal())
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

        professor.setId((Long) result.get("o_id"));
        professor.setEmail((String) result.get("o_email"));
        professor.setFirstName((String) result.get("o_firstname"));
        professor.setLastName((String) result.get("o_lastname"));
        professor.setPassword((String) result.get("o_password"));
        professor.setRole(roleDao.findById(((BigDecimal) result.get("o_role")).longValue()));

        return professor;
    }
}
