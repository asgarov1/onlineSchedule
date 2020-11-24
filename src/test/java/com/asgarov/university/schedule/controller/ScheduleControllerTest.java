package com.asgarov.university.schedule.controller;

import com.asgarov.university.schedule.Runner;
import com.asgarov.university.schedule.domain.Student;
import com.asgarov.university.schedule.domain.dto.ScheduleRequestDTO;
import com.asgarov.university.schedule.service.StudentService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringJUnitWebConfig(classes = {Runner.class})
@SpringBootTest
class ScheduleControllerTest {

    private MockMvc mockMvc;

    private static final String SCHEDULE_PATH = "/schedule";

    @Autowired
    private ScheduleController scheduleController;

    @Autowired
    StudentService studentService;

    @BeforeEach
    void setup() {
        FormattingConversionService conversionService = new FormattingConversionService();
        conversionService.addConverter(new StringToScheduleRequestDTOConverter());

        mockMvc = MockMvcBuilders
                .standaloneSetup(scheduleController)
                .setConversionService(conversionService)
                .setViewResolvers(viewResolver())
                .build();
    }

    @Test
    public void indexShouldReturnScheduleViewWithStatusOk() throws Exception {
        this.mockMvc.perform(get(SCHEDULE_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("schedule"));
    }

    @Test
    public void showScheduleShouldWork() throws Exception {
        Student student = studentService.findAll().get(0);

        this.mockMvc.perform(get(SCHEDULE_PATH + "/showSchedule")
                .param("scheduleRequestDTO", student.getId() + ",STUDENT,2020-04-01,2020-04-29"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("schedule"));
    }

    private static class StringToScheduleRequestDTOConverter implements Converter<String, ScheduleRequestDTO> {
        @Override
        public ScheduleRequestDTO convert(String from) {
            String[] data = from.split(",");
            return new ScheduleRequestDTO(Long.valueOf(data[0]), data[1], data[2], data[3]);
        }
    }

}
