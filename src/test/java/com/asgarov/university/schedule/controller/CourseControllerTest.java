package com.asgarov.university.schedule.controller;

import com.asgarov.university.schedule.Runner;
import com.asgarov.university.schedule.domain.Course;
import com.asgarov.university.schedule.domain.Student;
import com.asgarov.university.schedule.service.CourseService;
import com.asgarov.university.schedule.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.asgarov.university.schedule.controller.LectureControllerTest.StringToLectureDTOConverter;
import static com.asgarov.university.schedule.util.Resolver.viewResolver;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringJUnitWebConfig(classes = {Runner.class})
@SpringBootTest
class CourseControllerTest {

    private static final String COURSE_PATH = "/course";

    @Autowired
    CourseController courseController;

    @Autowired
    StudentService studentService;

    @Autowired
    CourseService courseService;

    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        FormattingConversionService conversionService = new FormattingConversionService();
        conversionService.addConverter(new StringToLectureDTOConverter());

        mockMvc = MockMvcBuilders
                .standaloneSetup(courseController)
                .setConversionService(conversionService)
                .setViewResolvers(viewResolver())
                .build();
    }

    @Test
    public void indexShouldReturnCourseViewWithStatusOk() throws Exception {
        this.mockMvc.perform(get(COURSE_PATH)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("course"));
    }

    @Test
    public void searchCoursesByIdShouldWork() throws Exception {
        this.mockMvc.perform(get(COURSE_PATH + "/searchCoursesById").param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("course"));
    }

    @Test
    public void registerStudentShouldWork() throws Exception {
        String courseId = "1";
        Student student = new Student("John", "Michaelson", Student.Degree.DOCTORATE);
        studentService.create(student);

        this.mockMvc.perform(post(COURSE_PATH + "/" + courseId + "/registerStudent")
                .param("studentId", student.getId().toString()))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/course/" + courseId + "/students"));
    }

    @Test
    public void addLectureShouldWork() throws Exception {
        String courseId = "1";
        mockMvc.perform(post(COURSE_PATH + "/" + courseId + "/addLecture")
                .param("lectureDTO", "2011-11-11,01:01,1,1"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/course/" + courseId + "/lectures"));
    }

    @Test
    public void addNewCourseShouldWork() throws Exception {
        String name = "New Course";
        String professorId = "1";

        this.mockMvc.perform(post(COURSE_PATH)
                .param("name", name)
                .param("professorId", professorId))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:" + COURSE_PATH));
    }

    @DirtiesContext
    @Test
    public void updateCourseShouldWork() throws Exception {
        String courseId = "2";
        String courseName = "Test Course";
        String professorId = "2";

        this.mockMvc.perform(put(COURSE_PATH + "/" + courseId)
                .param("courseName", courseName)
                .param("professorId", professorId))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:" + COURSE_PATH));
    }

    @Test
    public void showCourseLecturesShouldWork() throws Exception {
        String courseId = "1";

        this.mockMvc.perform(get(COURSE_PATH + "/" + courseId + "/lectures"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("courseLectures"));
    }

    @Test
    public void showCourseStudentsShouldWork() throws Exception {
        String courseId = "2";

        this.mockMvc.perform(get(COURSE_PATH + "/" + courseId + "/students"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("courseStudents"));
    }

    @Test
    public void deleteCourseShouldWork() throws Exception {
        String courseId = "1";

        this.mockMvc.perform(delete(COURSE_PATH + "/" + courseId))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:" + COURSE_PATH));
    }

    @Test
    public void removeStudentFromCourseShouldWork() throws Exception {
        Course course = courseService.findAll().get(0);
        String courseId = course.getId().toString();
        String studentId = course.getRegisteredStudents().get(0).getId().toString();

        this.mockMvc.perform(delete(COURSE_PATH + "/" + courseId + "/students/" + studentId))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:" + COURSE_PATH + "/" + courseId + "/students"));
    }

    @Test
    public void removeLectureFromCourseShouldWork() throws Exception {
        String courseId = "1";
        String lectureId = "1";

        this.mockMvc.perform(delete(COURSE_PATH + "/" + courseId + "/lectures/" + lectureId))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:" + COURSE_PATH + "/" + courseId + "/lectures"));
    }

}
