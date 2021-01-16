package com.asgarov.university.schedule.controller;

import com.asgarov.university.schedule.dao.exception.DaoException;
import com.asgarov.university.schedule.domain.Course;
import com.asgarov.university.schedule.domain.Lecture;
import com.asgarov.university.schedule.domain.Professor;
import com.asgarov.university.schedule.domain.dto.LectureDTO;
import com.asgarov.university.schedule.service.CourseService;
import com.asgarov.university.schedule.service.LectureService;
import com.asgarov.university.schedule.service.ProfessorService;
import com.asgarov.university.schedule.service.RoomService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("course")
public class CourseController {
    private CourseService courseService;
    private ProfessorService professorService;
    private RoomService roomService;
    private LectureService lectureService;

    public CourseController(CourseService courseService, ProfessorService professorService, RoomService roomService, LectureService lectureService) {
        this.courseService = courseService;
        this.professorService = professorService;
        this.roomService = roomService;
        this.lectureService = lectureService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("courses", courseService.findAll());
        return "course";
    }

    @GetMapping("/searchCoursesById")
    public String searchCoursesById(@RequestParam Long id, Model model) {
        try {
            model.addAttribute("courses", Collections.singletonList(courseService.findById(id)));
        } catch (EmptyResultDataAccessException e) {
            // Nothing found under the id - nothing to handle
        }
        return "course";
    }

    @PostMapping("/{id}/registerStudent")
    public String registerStudent(@PathVariable Long id, @RequestParam Long studentId) {
        courseService.registerStudent(id, studentId);
        return "redirect:/course/" + id + "/students";
    }

    @PostMapping("/{id}/addLecture")
    public String addLecture(@PathVariable Long id, LectureDTO lectureDTO) {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.parse(lectureDTO.getDate()), LocalTime.parse(lectureDTO.getTime()));
        Long lectureId = lectureService.create(new Lecture(localDateTime, roomService.findById(lectureDTO.getRoomId())));
        courseService.scheduleLecture(id, lectureId);
        return "redirect:/course/" + id + "/lectures";
    }

    @PostMapping
    public String addNewCourse(@RequestParam String name, @RequestParam Long professorId) {
        Course course = new Course();
        course.setName(name);
        course.setProfessor(professorService.findById(professorId));
        courseService.create(course);
        return "redirect:/course";
    }

    @PutMapping("/{id}")
    public String updateCourse(@PathVariable Long id, @RequestParam String courseName, @RequestParam Long professorId)
            throws DaoException {
        Course course = courseService.findById(id);
        course.setName(courseName);
        course.setProfessor(professorService.findById(professorId));
        courseService.update(course);
        return "redirect:/course";
    }


    @GetMapping("/{id}/lectures")
    public String showCourseLectures(@PathVariable Long id, Model model) {
        Course course = courseService.findById(id);
        model.addAttribute("course", course);
        model.addAttribute("lectures", course.getLectures());
        model.addAttribute("rooms", roomService.findAll());
        model.addAttribute("lectureDTO", new LectureDTO());
        return "courseLectures";
    }

    @GetMapping("/{id}/students")
    public String showCourseStudents(@PathVariable Long id, Model model) {
        Course course = courseService.findById(id);
        model.addAttribute("course", course);
        model.addAttribute("students", course.getRegisteredStudents());
        model.addAttribute("notRegisteredStudents", courseService.getNotRegisteredStudents(course));
        return "courseStudents";
    }

    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable Long id, Model model) throws DaoException {
        courseService.deleteById(id);
        return "redirect:/course";
    }

    @DeleteMapping("/{id}/students/{studentId}")
    public String removeStudentFromCourse(@PathVariable Long id, @PathVariable Long studentId) {
        Course course = courseService.findById(id);
        courseService.unregisterStudent(course, studentId);
        return "redirect:/course/" + id + "/students";
    }

    @DeleteMapping("{id}/lectures/{lectureId}")
    public String removeLectureFromCourse(@PathVariable Long id, @PathVariable Long lectureId) {
        courseService.removeLecture(lectureId);
        return "redirect:/course/" + id + "/lectures";
    }

    @ModelAttribute("courseService")
    public CourseService courseService() {
        return courseService;
    }

    @ModelAttribute("professors")
    public List<Professor> professors() {
        return professorService.findAll();
    }

}
