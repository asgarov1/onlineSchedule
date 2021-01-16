package com.asgarov.university.schedule.service;

import com.asgarov.university.schedule.dao.ProfessorDao;
import com.asgarov.university.schedule.domain.Course;
import com.asgarov.university.schedule.domain.Professor;
import com.asgarov.university.schedule.domain.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorService extends AbstractDaoService<Long, Professor> {

    private StudentService studentService;

    public ProfessorService(final ProfessorDao professorDao, final StudentService studentService) {
        super(professorDao);
        this.studentService = studentService;
    }

    public List<Student> getAllStudentsForCourse(Course course){
        return studentService.findAllStudentsByCourseId(course.getId());
    }
}
