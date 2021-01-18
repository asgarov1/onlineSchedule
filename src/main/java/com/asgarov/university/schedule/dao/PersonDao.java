package com.asgarov.university.schedule.dao;


import com.asgarov.university.schedule.domain.Person;
import com.asgarov.university.schedule.domain.Professor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Repository
public class PersonDao extends AbstractDao<String, Person> {

    private final RoleDao roleDao;

    public PersonDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    protected String mapToKey(Object key) {
        return (String) key;
    }

    @Override
    protected Person rowMapper(final ResultSet resultSet, final int rowNum) throws SQLException {
        Person person = new Person();
        person.setEmail(resultSet.getString("email"));
        person.setFirstName(resultSet.getString("firstName"));
        person.setLastName(resultSet.getString("lastName"));
        person.setPassword(resultSet.getString("password"));
        person.setRole(roleDao.findById(resultSet.getLong("role_id")));
        return person;
    }

    @Override
    protected SqlParameterSource getParameterMap(Person person) {
        return new MapSqlParameterSource()
                .addValue("p_email", person.getEmail())
                .addValue("p_firstname", person.getFirstName())
                .addValue("p_lastname", person.getLastName())
                .addValue("p_password", person.getPassword())
                .addValue("p_role_id", person.getRole().ordinal())
                .addValue("p_id", person.getEmail());
    }

    @Override
    protected String getCreateProcedureName() {
        return "create_person";
    }

    @Override
    protected String getUpdateProcedureName() {
        return "update_person";
    }

    @Override
    protected String getDeleteProcedureName() {
        return "delete_person";
    }

    @Override
    protected String getFindAllProcedure() {
        return "find_all_persons";
    }

    @Override
    protected String getFindByIdProcedureName() {
        return "find_by_id_person";
    }

    @Override
    protected Person instantiateFromMap(Map<String, Object> result) {
        Person person = new Person();

        person.setEmail((String) result.get("o_email"));
        person.setFirstName((String) result.get("o_firstname"));
        person.setLastName((String) result.get("o_lastname"));
        person.setPassword((String) result.get("o_password"));
        person.setRole(roleDao.findById(((BigDecimal) result.get("o_role_id")).longValue()));

        return person;
    }
}
