package com.asgarov.university.schedule.service;

import com.asgarov.university.schedule.domain.Professor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProfessorServiceTest {

    @Autowired
    ProfessorService professorService;

    @Autowired
    CourseService courseService;

    @Test
    void createShouldWork() {
        Professor professor = new Professor("John", "Maximilianov");
        Long professorId = professorService.create(professor).getId();
        assertNotNull(professorService.findById(professorId));
    }

    @Test
    void updateShouldWork() {
        Professor professor = professorService.findAll().get(0);
        professor.setFirstName("Sergey");
        professor.setLastName("Nemchinskiy");

        professorService.update(professor);

        Professor actualProfessor = professorService.findById(professor.getId());
        assertEquals(professor.getFullName(), actualProfessor.getFullName());
    }

    @Test
    void findAllShouldWork() {
        List<Professor> professors = professorService.findAll();
        assertNotNull(professors);
    }

    @Test
    void findByIdShouldWork() {
        List<Professor> professors = professorService.findAll();
        Professor expected = professors.get(0);
        assertNotNull(professorService.findById(expected.getId()));
    }

    @Test
    void deleteByIdShouldWork() {
        Professor professor = new Professor("John", "Wick");
        Long professorId = professorService.create(professor).getId();

        int sizeBeforeDelete = professorService.findAll().size();

        professorService.deleteById(professorId);

        int expectedSize = sizeBeforeDelete - 1;
        assertEquals(expectedSize, professorService.findAll().size());
    }
}
