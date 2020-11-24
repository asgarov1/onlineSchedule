package com.asgarov.university.schedule.repository;

import com.asgarov.university.schedule.domain.Course;
import com.asgarov.university.schedule.domain.Lecture;
import com.asgarov.university.schedule.domain.Professor;
import com.asgarov.university.schedule.domain.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {
    List<Course> findAll();

    Optional<Course> findByLecturesContaining(Lecture lecture);

    List<Course> findAllByProfessor(Professor professor);

    List<Course> findByRegisteredStudentsContains(Student student);
}
