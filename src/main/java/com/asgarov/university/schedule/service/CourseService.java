package com.asgarov.university.schedule.service;

import com.asgarov.university.schedule.domain.Course;
import com.asgarov.university.schedule.domain.Lecture;
import com.asgarov.university.schedule.domain.Professor;
import com.asgarov.university.schedule.domain.Student;
import com.asgarov.university.schedule.repository.CourseRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CourseService extends AbstractService<Course, Long> {

    private final StudentService studentService;
    private final LectureService lectureService;
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository, StudentService studentService, LectureService lectureService) {
        super(courseRepository);
        this.courseRepository = courseRepository;
        this.studentService = studentService;
        this.lectureService = lectureService;
    }

    public void registerStudents(Course course, List<Student> students) {
        students.forEach(course::addStudent);
        update(course);
    }

    public List<Course> findProfessorsCourses(final Professor professor) {
        return courseRepository.findAllByProfessor(professor);
    }

    public List<Student> getNotRegisteredStudents(Course course) {
        List<Student> notRegisteredStudents = studentService.findAll();
        notRegisteredStudents.removeAll(course.getRegisteredStudents());
        return notRegisteredStudents;
    }

    public void registerStudent(Long courseId, Long studentId) {
        Course course = findById(courseId);
        course.addStudent(studentService.findById(studentId));
        update(course);
    }

    public void scheduleLecture(Long courseId, Long lectureId) {
        Course course = findById(courseId);
        course.addLecture(lectureService.findById(lectureId));
        update(course);
    }

    public void unregisterStudent(Long courseId, Long studentId) {
        Course course = findById(courseId);
        course.removeStudent(studentService.findById(studentId));
        update(course);
    }

    public void removeLecture(Long courseId, Long lectureId) {
        Course course = findById(courseId);
        course.removeLecture(lectureService.findById(lectureId));
        update(course);
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Course findByLectureId(Long lectureId) {
        Lecture lecture = lectureService.findById(lectureId);
        return courseRepository.findByLecturesContaining(lecture).orElseThrow(EntityNotFoundException::new);
    }

    public List<Course> findStudentsCourses(Student student) {
        return courseRepository.findByRegisteredStudentsContains(student);
    }
}
