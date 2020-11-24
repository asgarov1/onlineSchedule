package com.asgarov.university.schedule.controller;

import com.asgarov.university.schedule.Runner;
import com.asgarov.university.schedule.domain.Professor;
import com.asgarov.university.schedule.service.ProfessorService;
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
class ProfessorControllerTest {

    private MockMvc mockMvc;

    private static final String PROFESSOR_PATH = "/professor";

    @Autowired
    private ProfessorController professorController;

    @Autowired
    private ProfessorService professorService;

    @BeforeEach
    void setup() {
        FormattingConversionService conversionService = new FormattingConversionService();
        conversionService.addConverter(new StringToProfessorConverter());

        mockMvc = MockMvcBuilders
                .standaloneSetup(professorController)
                .setConversionService(conversionService)
                .setViewResolvers(viewResolver())
                .build();
    }

    @Test
    public void indexShouldReturnProfessorViewWithStatusOk() throws Exception {
        this.mockMvc.perform(get(PROFESSOR_PATH))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("professor"));
    }

    @Test
    public void searchProfessorsByIdShouldWork() throws Exception {
        this.mockMvc.perform(get(PROFESSOR_PATH + "/searchProfessorsById")
                .param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("professor"));
    }

    @Test
    public void addNewProfessorShouldWork() throws Throwable {
        mockMvc.perform(post(PROFESSOR_PATH)
                .param("professor", "Max,Maximov,maximov@mail.ru,pass"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:" + PROFESSOR_PATH));
    }

    @Test
    public void deleteProfessorShouldWork() throws Exception {
        Professor professor = new Professor("Dummy", "Damidson");
        professorService.create(professor);

        this.mockMvc.perform(delete(PROFESSOR_PATH + "/" + professor.getId()))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/professor"));
    }

    @Test
    public void updateProfessorShouldWork() throws Exception {
        String professorId = "1";

        this.mockMvc.perform(put(PROFESSOR_PATH + "/" + professorId)
                .param("professor", "Max,Maximov,maximov@mail.ru,pass"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:" + PROFESSOR_PATH));
    }

    private static class StringToProfessorConverter implements Converter<String, Professor> {
        @Override
        public Professor convert(String from) {
            String[] data = from.split(",");
            return new Professor(data[0], data[1], data[2], data[3]);
        }
    }

}
