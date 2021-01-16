package com.asgarov.university.schedule.service;

import com.asgarov.university.schedule.domain.*;
import com.asgarov.university.schedule.domain.dto.ScheduleRequestDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private CourseService courseService;
    private StudentService studentService;
    private ProfessorService professorService;

    public ScheduleService(CourseService courseService, StudentService studentService, ProfessorService professorService) {
        this.courseService = courseService;
        this.studentService = studentService;
        this.professorService = professorService;
    }

    public List<DaySchedule> getPersonsSchedule(Person person) {
        List<Course> courses = new ArrayList<>();
        if (person instanceof Student) {
            Student student = (Student) person;
            courses = courseService.findStudentsCourses(student);
        } else if (person instanceof Professor) {
            Professor professor = (Professor) person;
            courses = courseService.findProfessorsCourses(professor);
        }

        Map<LocalDate, List<Lecture>> schedules = courses.stream()
                .map(Course::getLectures)
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(Lecture::getDateTime))
                .collect(Collectors.groupingBy(lecture -> lecture.getDateTime().toLocalDate()));

        List<DaySchedule> daySchedules = new ArrayList<>();
        schedules.forEach((date, lecture) -> {
            daySchedules.add(new DaySchedule(date, lecture));
        });

        daySchedules.sort(Comparator.comparing(DaySchedule::getLocalDate));

        return daySchedules;
    }

    public DaySchedule getTodayScheduleForPerson(final Person person) {
        List<DaySchedule> daySchedules = getPersonsSchedule(person);
        return daySchedules.stream()
                .filter(daySchedule -> daySchedule.getLocalDate().equals(LocalDate.now()))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    public List<DaySchedule> getSchedule(Person person, LocalDate dateFrom, LocalDate dateTo) {
        List<DaySchedule> daySchedules = getPersonsSchedule(person);
        return daySchedules.stream()
                .filter(daySchedule -> daySchedule.getLocalDate().isAfter(dateFrom))
                .filter(daySchedule -> daySchedule.getLocalDate().isBefore(dateTo))
                .collect(Collectors.toList());
    }

    public List<DaySchedule> getSchedule(ScheduleRequestDTO scheduleRequest) {
        LocalDate from = LocalDate.parse(scheduleRequest.getDateFrom());
        LocalDate to = LocalDate.parse(scheduleRequest.getDateTo());

        if (scheduleRequest.getRole().equals(Role.STUDENT.toString())) {
            Student student = studentService.findById(scheduleRequest.getId());
            return getSchedule(student, from, to);
        } else {
            Professor professor = professorService.findById(scheduleRequest.getId());
            return getSchedule(professor, from, to);
        }
    }

}
