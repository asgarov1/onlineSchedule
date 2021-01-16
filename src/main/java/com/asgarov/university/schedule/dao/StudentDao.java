package com.asgarov.university.schedule.dao;


import com.asgarov.university.schedule.dao.exception.DaoException;
import com.asgarov.university.schedule.domain.CourseStudent;
import com.asgarov.university.schedule.domain.Role;
import com.asgarov.university.schedule.domain.Student;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StudentDao extends AbstractDao<Long, Student> {

    private final CourseStudentDao courseStudentDao;

    public StudentDao(final CourseStudentDao courseStudentDao) {
        this.courseStudentDao = courseStudentDao;
    }

    @Override
    protected String tableName() {
        return "STUDENT";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE " + tableName()
                + " SET email = ?, firstName = ?, lastName = ?, password = ?, role = ?, degree = ? WHERE id = ?";
    }

    @Override
    protected Student rowMapper(final ResultSet resultSet, final int rowNum) throws SQLException {
        Student student = new Student();
        student.setId(resultSet.getLong("id"));
        student.setEmail(resultSet.getString("email"));
        student.setFirstName(resultSet.getString("firstName"));
        student.setLastName(resultSet.getString("lastName"));
        student.setPassword(resultSet.getString("password"));
        student.setRole(Role.valueOf(resultSet.getString("role")));
        student.setDegree(Student.Degree.valueOf(resultSet.getString("degree")));
        return student;
    }

    @Override
    protected Object[] updateParameters(final Student student) {
        return new Object[] { student.getEmail(), student.getFirstName(), student.getLastName(),
                student.getPassword(), student.getRole().toString(), student.getDegree().toString(), student.getId() };
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
                .addValue("p_role", student.getRole())
                .addValue("p_degree", student.getDegree())
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
}
