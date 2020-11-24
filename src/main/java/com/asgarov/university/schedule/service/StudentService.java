package com.asgarov.university.schedule.service;

import com.asgarov.university.schedule.domain.Student;
import com.asgarov.university.schedule.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService extends AbstractService<Student, Long> {
    private final StudentRepository studentRepository;

    public StudentService(final StudentRepository studentRepository) {
        super(studentRepository);
        this.studentRepository = studentRepository;
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }
}
