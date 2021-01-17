package com.asgarov.university.schedule.dao;


import com.asgarov.university.schedule.dao.exception.DaoException;
import com.asgarov.university.schedule.domain.CourseStudent;
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
    private final RoleDao roleDao;
    private final DegreeDao degreeDao;

    public StudentDao(CourseStudentDao courseStudentDao, RoleDao roleDao, DegreeDao degreeDao) {
        this.courseStudentDao = courseStudentDao;
        this.roleDao = roleDao;
        this.degreeDao = degreeDao;
    }

    @Override
    protected Student rowMapper(final ResultSet resultSet, final int rowNum) throws SQLException {
        Student student = new Student();
        student.setId(resultSet.getLong("id"));
        student.setEmail(resultSet.getString("email"));
        student.setFirstName(resultSet.getString("firstName"));
        student.setLastName(resultSet.getString("lastName"));
        student.setPassword(resultSet.getString("password"));
        student.setRole(roleDao.findById(resultSet.getLong("role_id")));
        student.setDegree(degreeDao.findById(resultSet.getLong("degree_id")));
        return student;
    }

    @Override
    public void deleteById(final Long id) throws DaoException {
        courseStudentDao.deleteByStudentId(id);
        super.deleteById(id);
    }

    public List<Student> findAllStudentsByCourseId(final Long courseId) {
        List<CourseStudent> courseStudentList = courseStudentDao.findAllByCourseId(courseId);
        List<Student> requestedStudents = new ArrayList<>();
        courseStudentList.forEach(cs -> requestedStudents.add(findById(cs.getStudentId())));
        return requestedStudents;
    }

    @Override
    protected SqlParameterSource getParameterMap(Student student) {
        return new MapSqlParameterSource()
                .addValue("p_email", student.getEmail())
                .addValue("p_firstname", student.getFirstName())
                .addValue("p_lastname", student.getLastName())
                .addValue("p_password", student.getPassword())
                .addValue("p_role", student.getRole().ordinal())
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
        student.setEmail((String) result.get("o_email"));
        student.setFirstName((String) result.get("o_firstname"));
        student.setLastName((String) result.get("o_lastname"));
        student.setPassword((String) result.get("o_password"));
        student.setRole(roleDao.findById(((BigDecimal) result.get("o_role")).longValue()));
        student.setDegree(degreeDao.findById(((BigDecimal) result.get("o_degree")).longValue()));

        return student;
    }
}
