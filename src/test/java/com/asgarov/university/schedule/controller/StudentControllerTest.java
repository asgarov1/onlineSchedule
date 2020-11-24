package com.asgarov.university.schedule.controller;

import com.asgarov.university.schedule.Runner;
import com.asgarov.university.schedule.domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.asgarov.university.schedule.util.Resolver.viewResolver;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringJUnitWebConfig(classes = {Runner.class})
@SpringBootTest
class StudentControllerTest {

    private MockMvc mockMvc;

    private static final String STUDENT_PATH = "/student";

    @Autowired
    private StudentController studentController;

    @BeforeEach
    void setup() {
        FormattingConversionService conversionService = new FormattingConversionService();
        conversionService.addConverter(new StringToStudentConverter());

        mockMvc = MockMvcBuilders
                .standaloneSetup(studentController)
                .setConversionService(conversionService)
                .setViewResolvers(viewResolver())
                .build();
    }

    @Test
    public void indexShouldReturnStudentViewWithStatusOk() throws Exception {
        this.mockMvc.perform(get(STUDENT_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("student"));
    }

    @Test
    public void searchStudentsByIdShouldWork() throws Exception {
        this.mockMvc.perform(get(STUDENT_PATH + "/searchStudentsById")
                .param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("student"));
    }

    @Test
    public void addNewStudentShouldWork() throws Throwable {
        mockMvc.perform(post(STUDENT_PATH)
                .param("student", "Max,Maximov,maximov@mail.ru,pass,DOCTORATE"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("student"));
    }

    @Test
    public void deleteStudentShouldWork() throws Exception {
        String studentId = "1";

        this.mockMvc.perform(delete(STUDENT_PATH + "/" + studentId))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/student"));
    }

    @Test
    public void updateStudentShouldWork() throws Exception {
        String studentId = "1";

        this.mockMvc.perform(put(STUDENT_PATH + "/" + studentId)
                .param("student", "Max,Maximov,maximov@mail.ru,pass,BACHELOR"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:" + STUDENT_PATH));
    }

    private static class StringToStudentConverter implements Converter<String, Student> {
        @Override
        public Student convert(String from) {
            String[] data = from.split(",");
            return new Student(data[0], data[1], data[2], data[3], Student.Degree.valueOf(data[4]));
        }
    }

}
