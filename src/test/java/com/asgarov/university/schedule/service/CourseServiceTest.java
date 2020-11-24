package com.asgarov.university.schedule.service;

import com.asgarov.university.schedule.Runner;
import com.asgarov.university.schedule.domain.Course;
import com.asgarov.university.schedule.domain.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;

import static com.asgarov.university.schedule.domain.Student.Degree.DOCTORATE;
import static com.asgarov.university.schedule.domain.Student.Degree.MASTER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ContextConfiguration(classes = {Runner.class})
@SpringBootTest
public class CourseServiceTest {

    @Autowired
    CourseService courseService;

    @Autowired
    StudentService studentService;

    @Autowired
    LectureService lectureService;

    @Autowired
    RoomService roomService;

    @Autowired
    ProfessorService professorService;

    @Test
    void registerStudents() {
        Student depp = new Student("Johnny", "Depp", DOCTORATE);
        studentService.create(depp);

        Student jolie = new Student("Angelina", "Jolie", MASTER);
        studentService.create(jolie);
        List<Student> newStudents = Arrays.asList(depp, jolie);

        Course course = new Course("Acting Classes for over 50");
        courseService.create(course);

        int expected = course.getRegisteredStudents().size() + newStudents.size();
        courseService.registerStudents(course, Arrays.asList(depp, jolie));

        List<Student> registeredStudents = courseService.findById(course.getId()).getRegisteredStudents();
        assertEquals(expected, registeredStudents.size());
    }

    @Test
    void createShouldWork() {
        Course course = new Course("Biology");
        courseService.create(course);

        Course actual = courseService.findById(course.getId());
        assertNotNull(actual);
    }

    @Test
    void updateShouldWork() {
        Course course = courseService.findAll().get(0);

        course.setName("Docker 101");
        courseService.update(course);

        Course actualCourse = courseService.findById(course.getId());
        assertEquals(course.getName(), actualCourse.getName());
    }

    @Test
    void findByIdShouldWork() {
        List<Course> courses = courseService.findAll();
        Course course = courses.get(0);

        assertNotNull(courseService.findById(course.getId()));
    }

    @Test
    void findAllShouldWork() {
        List<Course> courses = courseService.findAll();
        assertNotNull(courses);
    }

    @Test
    void deleteByIdShouldWork() {
        List<Course> courses = courseService.findAll();

        Long courseId = courses.get(0).getId();
        courseService.deleteById(courseId);

        int expectedSize = courses.size() - 1;
        assertEquals(expectedSize, courseService.findAll().size());
    }
}
