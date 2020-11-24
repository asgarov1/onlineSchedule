package com.asgarov.university.schedule.controller;

import com.asgarov.university.schedule.domain.Student;
import com.asgarov.university.schedule.domain.Student.Degree;
import com.asgarov.university.schedule.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;

@Controller
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("students", studentService.findAll());
        return "student";
    }

    @GetMapping("/searchStudentsById")
    public String searchStudentsById(@RequestParam Long id, Model model) {
        try {
            model.addAttribute("students", Collections.singletonList(studentService.findById(id)));
        } catch (EntityNotFoundException e) {
            //nothing to handle
        }
        return "student";
    }

    @PostMapping
    public String addNewStudent(Student student, Model model) {
        studentService.create(student);
        model.addAttribute("students", studentService.findAll());
        return "student";
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteById(id);
        return "redirect:/student";
    }

    @PutMapping("/{id}")
    public String updateStudent(@PathVariable Long id, Student student) {
        student.setId(id);
        studentService.update(student);
        return "redirect:/student";
    }

    @ModelAttribute("newStudent")
    public Student newStudent() {
        return new Student();
    }

    @ModelAttribute("degrees")
    public Degree[] degree() {
        return Degree.values();
    }
}
