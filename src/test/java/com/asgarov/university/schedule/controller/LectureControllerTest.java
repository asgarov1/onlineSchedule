package com.asgarov.university.schedule.controller;

import com.asgarov.university.schedule.Runner;
import com.asgarov.university.schedule.domain.dto.LectureDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.test.annotation.DirtiesContext;
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
class LectureControllerTest {

    private MockMvc mockMvc;

    private static final String LECTURE_PATH = "/lecture";

    @Autowired
    private LectureController lectureController;

    @BeforeEach
    void setup() {
        FormattingConversionService conversionService = new FormattingConversionService();
        conversionService.addConverter(new StringToLectureDTOConverter());

        mockMvc = MockMvcBuilders
                .standaloneSetup(lectureController)
                .setConversionService(conversionService)
                .setViewResolvers(viewResolver())
                .build();
    }

    @Test
    public void indexShouldReturnLectureViewWithStatusOk() throws Exception {
        this.mockMvc.perform(get(LECTURE_PATH)
                .param("page", "1")
                .param("size", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("lecture"));
    }

    @Test
    public void searchLecturesByIdShouldWork() throws Exception {
        this.mockMvc.perform(get(LECTURE_PATH + "/searchLecturesById")
                .param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("lecture"));
    }

    @Test
    public void addNewLectureShouldWork() throws Throwable {
        mockMvc.perform(post(LECTURE_PATH)
                .param("lectureDTO", "2011-11-11,01:01,1,1"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:" + LECTURE_PATH));
    }

    @Test
    public void deleteLectureShouldWork() throws Exception {
        String lectureId = "1";
        this.mockMvc.perform(delete(LECTURE_PATH + "/" + lectureId))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:" + LECTURE_PATH));
    }

    @Test
    @DirtiesContext
    public void updateLectureShouldWork() throws Exception {
        String lectureId = "2";
        this.mockMvc.perform(delete(LECTURE_PATH + "/" + lectureId)
                .param("lectureDTO", "2011-11-11,01:01,1,1"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:" + LECTURE_PATH));
    }

    public static class StringToLectureDTOConverter implements Converter<String, LectureDTO> {
        @Override
        public LectureDTO convert(String from) {
            String[] data = from.split(",");
            return new LectureDTO(data[0], data[1], Long.parseLong(data[2]), Long.parseLong(data[3]));
        }
    }

}
