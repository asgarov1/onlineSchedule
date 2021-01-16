package com.asgarov.university.schedule.dao;

import com.asgarov.university.schedule.dao.exception.DaoException;
import com.asgarov.university.schedule.domain.Course;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class CourseDao extends AbstractDao<Long, Course> {

    private final LectureDao lectureDao;
    private final CourseStudentDao courseStudentDao;
    private final CourseLectureDao courseLectureDao;
    private final ProfessorDao professorDao;
    private final StudentDao studentDao;

    public CourseDao(
            final LectureDao lectureDao,
            final CourseStudentDao courseStudentDao,
            final CourseLectureDao courseLectureDao,
            final ProfessorDao professorDao,
            final StudentDao studentDao) {
        this.lectureDao = lectureDao;
        this.courseStudentDao = courseStudentDao;
        this.courseLectureDao = courseLectureDao;
        this.professorDao = professorDao;
        this.studentDao = studentDao;
    }

    public SqlParameterSource getParameterMap(Course course) {
        return new MapSqlParameterSource()
                .addValue("p_name", course.getName())
                .addValue("p_professor_id", course.getProfessor().getId())
                .addValue("p_id", course.getId());
    }

    @Override
    protected String getCreateProcedureName() {
        return "create_course";
    }

    @Override
    protected String getUpdateProcedureName() {
        return "update_course";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE " + tableName() + " SET name = ?, professor_id = ? WHERE id = ?";
    }

    @Override
    protected Course rowMapper(final ResultSet resultSet, final int rowNum) throws SQLException {
        Course course = new Course();
        course.setId(resultSet.getLong("id"));
        course.setName(resultSet.getString("name"));

        Long professorId = resultSet.getLong("professor_id");
        course.setProfessor(professorDao.findById(professorId));
        course.setLectures(lectureDao.findAllByCourseId(course.getId()));
        course.setRegisteredStudents(studentDao.findAllStudentsByCourseId(course.getId()));

        return course;
    }

    @Override
    protected String tableName() {
        return "course";
    }

    @Override
    protected Object[] updateParameters(final Course course) {
        return new Object[] { course.getName(), course.getProfessor().getId(), course.getId() };
    }

    @Override
    public void deleteById(final Long id) throws DaoException {
        courseLectureDao.deleteByCourseId(id);
        courseStudentDao.deleteByCourseId(id);
        super.deleteById(id);
    }
}
