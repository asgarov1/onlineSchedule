package com.asgarov.university.schedule.service;

import com.asgarov.university.schedule.domain.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {
    private StudentService studentService;
    private ProfessorService professorService;

    public PersonService(StudentService studentService, ProfessorService professorService) {
        this.studentService = studentService;
        this.professorService = professorService;
    }

    public List<Person> findAll() {
        List<Person> persons = new ArrayList<>(studentService.findAll());
        persons.addAll(professorService.findAll());
        return persons;
    }

}
