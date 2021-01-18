package com.asgarov.university.schedule.dao;


import com.asgarov.university.schedule.dao.exception.DaoException;
import com.asgarov.university.schedule.domain.CourseStudent;
import com.asgarov.university.schedule.domain.Person;
import com.asgarov.university.schedule.domain.Student;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class StudentDao extends AbstractDao<Long, Student> {

    private final CourseStudentDao courseStudentDao;
    private final DegreeDao degreeDao;
    private final PersonDao personDao;

    public StudentDao(CourseStudentDao courseStudentDao, DegreeDao degreeDao, PersonDao personDao) {
        this.courseStudentDao = courseStudentDao;
        this.degreeDao = degreeDao;
        this.personDao = personDao;
    }

    @Override
    protected Student rowMapper(final ResultSet resultSet, final int rowNum) throws SQLException {
        Student student = new Student();
        student.setId(resultSet.getLong("id"));

        Person person = personDao.findById(resultSet.getString("person_id"));

        student.setEmail(person.getEmail());
        student.setFirstName(person.getFirstName());
        student.setLastName(person.getLastName());
        student.setPassword(person.getPassword());
        student.setRole(person.getRole());

        student.setDegree(degreeDao.findById(resultSet.getLong("degree_id")));
        return student;
    }

    @Override
    public void deleteById(final Long id) throws DaoException {
        courseStudentDao.deleteByStudentId(id);
        personDao.deleteById(findById(id).getEmail());
        super.deleteById(id);
    }

    public List<Student> findAllStudentsByCourseId(final Long courseId) {
        List<CourseStudent> courseStudentList = courseStudentDao.findAllByCourseId(courseId);
        List<Student> requestedStudents = new ArrayList<>();
        courseStudentList.forEach(cs -> requestedStudents.add(findById(cs.getStudentId())));
        return requestedStudents;
    }

    @Override
    public Long create(Student student) {
        personDao.create(student);
        return super.create(student);
    }

    @Override
    public void update(Student student) {
        personDao.update(student);
        super.update(student);
    }

    @Override
    protected SqlParameterSource getParameterMap(Student student) {
        return new MapSqlParameterSource()
                .addValue("p_person_id", student.getEmail())
                .addValue("p_degree", student.getDegree().ordinal())
                .addValue("p_id", student.getId());
    }

    @Override
    protected String getCreateProcedureName() {
        return "create_student";
    }

    @Override
    protected String getUpdateProcedureName() {
        return "update_student";
    }

    @Override
    protected String getDeleteProcedureName() {
        return "delete_student";
    }

    @Override
    protected String getFindAllProcedure() {
        return "find_all_students";
    }

    @Override
    protected String getFindByIdProcedureName() {
        return "find_by_id_student";
    }

    @Override
    protected Student instantiateFromMap(Map<String, Object> result) {
        Student student = new Student();
        student.setId((Long) result.get("o_id"));

        Person person = personDao.findById((String) result.get("o_person_id"));
        student.setEmail(person.getEmail());
        student.setFirstName(person.getFirstName());
        student.setLastName(person.getLastName());
        student.setPassword(person.getPassword());
        student.setRole(person.getRole());

        student.setDegree(degreeDao.findById(((BigDecimal) result.get("o_degree")).longValue()));

        return student;
    }

    @Override
    protected Long mapToKey(Object key) {
        return ((BigDecimal) (key)).longValue();
    }
}
