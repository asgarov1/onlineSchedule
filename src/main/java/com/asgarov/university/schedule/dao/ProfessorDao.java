package com.asgarov.university.schedule.dao;


import com.asgarov.university.schedule.dao.exception.DaoException;
import com.asgarov.university.schedule.domain.Person;
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

    private final PersonDao personDao;

    public ProfessorDao(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    protected Long mapToKey(Object key) {
        return ((BigDecimal) (key)).longValue();
    }

    @Override
    protected Professor rowMapper(final ResultSet resultSet, final int rowNum) throws SQLException {
        Professor professor = new Professor();
        professor.setId(resultSet.getLong("id"));

        Person person = personDao.findById(resultSet.getString("person_id"));

        professor.setEmail(person.getEmail());
        professor.setFirstName(person.getFirstName());
        professor.setLastName(person.getLastName());
        professor.setPassword(person.getPassword());
        professor.setRole(person.getRole());
        return professor;
    }

    @Override
    public Long create(Professor professor) {
        personDao.create(professor);
        return super.create(professor);
    }

    @Override
    public void update(Professor professor) {
        personDao.update(professor);
        super.update(professor);
    }

    @Override
    public void deleteById(Long id) throws DaoException {
        personDao.deleteById(findById(id).getEmail());
        super.deleteById(id);
    }

    @Override
    protected SqlParameterSource getParameterMap(Professor professor) {
        return new MapSqlParameterSource()
                .addValue("p_person_id", professor.getEmail())
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

        Person person = personDao.findById((String) result.get("o_person_id"));

        professor.setEmail(person.getEmail());
        professor.setFirstName(person.getFirstName());
        professor.setLastName(person.getLastName());
        professor.setPassword(person.getPassword());
        professor.setRole(person.getRole());

        return professor;
    }
}
