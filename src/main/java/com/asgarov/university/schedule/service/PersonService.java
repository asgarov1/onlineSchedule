package com.asgarov.university.schedule.service;

import com.asgarov.university.schedule.domain.Person;
import com.asgarov.university.schedule.domain.Role;
import com.asgarov.university.schedule.domain.dto.ScheduleRequestDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {
    private final StudentService studentService;
    private final ProfessorService professorService;

    public PersonService(StudentService studentService, ProfessorService professorService) {
        this.studentService = studentService;
        this.professorService = professorService;
    }

    public List<Person> findAll() {
        List<Person> persons = new ArrayList<>(studentService.findAll());
        persons.addAll(professorService.findAll());
        return persons;
    }

    public Person findPerson(ScheduleRequestDTO scheduleRequestDTO) {
        if (scheduleRequestDTO.getRole().equals(Role.STUDENT.toString())) {
            return studentService.findById(scheduleRequestDTO.getId());
        } else {
            return professorService.findById(scheduleRequestDTO.getId());
        }
    }
}
