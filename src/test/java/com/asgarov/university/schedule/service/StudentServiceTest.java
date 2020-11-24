package com.asgarov.university.schedule.service;

import com.asgarov.university.schedule.domain.Student;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class StudentServiceTest {

    StudentService studentService = Mockito.mock(StudentService.class);

    @Test
    void createShouldWork() {
        Student student = new Student("John", "Maximilianov", Student.Degree.MASTER);
        student.setId(1L);
        when(studentService.create(student)).thenReturn(student);
        studentService.create(student);

        when(studentService.findById(anyLong())).thenReturn(student);
        Student actual = studentService.findById(student.getId());
        assertEquals(student, actual);
        verify(studentService, times(1)).findById(anyLong());
    }

    @Test
    void updateShouldWork() {
        Student mockStudent = new Student("Mark", "Zukerberg", Student.Degree.DOCTORATE);
        mockStudent.setId(1L);
        when(studentService.findById(anyLong())).thenReturn(mockStudent);
        Student student = studentService.findById(1L);
        student.setFirstName("Sergey");
        student.setLastName("Nemchinskiy");

        studentService.update(student);

        Student actualStudent = studentService.findById(student.getId());
        assertEquals(student, actualStudent);
        verify(studentService, times(2)).findById(anyLong());
    }

    @Test
    void findAllShouldWork() {
        Student mockStudent = new Student("Mark", "Zukerberg", Student.Degree.DOCTORATE);
        when(studentService.findAll()).thenReturn(Collections.singletonList(mockStudent));

        List<Student> students = studentService.findAll();
        assertNotNull(students);
        verify(studentService, times(1)).findAll();
    }

    @Test
    void findByIdShouldWork() {
        Student mockStudent = new Student("Mark", "Zukerberg", Student.Degree.DOCTORATE);
        mockStudent.setId(1L);
        when(studentService.findAll()).thenReturn(Collections.singletonList(mockStudent));

        List<Student> students = studentService.findAll();
        Student expected = students.get(0);

        when(studentService.findById(anyLong())).thenReturn(mockStudent);
        Student actual = studentService.findById(expected.getId());
        assertEquals(expected, actual);
        verify(studentService, times(1)).findById(anyLong());
    }

    @Test
    void deleteByIdShouldWork() {
        Student student = new Student("John", "Maximilianov", Student.Degree.MASTER);
        List<Student> students = new ArrayList<>();

        doAnswer(iom -> {
            students.add(student);
            student.setId(1L);
            return student;
        }).when(studentService).create(student);
        Long studentId = studentService.create(student).getId();

        when(studentService.findAll()).thenReturn(students);
        int sizeBeforeDelete = studentService.findAll().size();

        doAnswer(iom -> students.remove(student)).when(studentService).deleteById(anyLong());
        studentService.deleteById(studentId);

        int expectedSize = sizeBeforeDelete - 1;
        assertEquals(expectedSize, studentService.findAll().size());
        verify(studentService, times(1)).deleteById(anyLong());
    }
}
