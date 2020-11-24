package com.asgarov.university.schedule.controller;

import com.asgarov.university.schedule.domain.Person;
import com.asgarov.university.schedule.domain.dto.ScheduleRequestDTO;
import com.asgarov.university.schedule.service.CourseService;
import com.asgarov.university.schedule.service.PersonService;
import com.asgarov.university.schedule.service.ScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final CourseService courseService;
    private final PersonService personService;

    public ScheduleController(ScheduleService scheduleService, CourseService courseService, PersonService personService) {
        this.scheduleService = scheduleService;
        this.courseService = courseService;
        this.personService = personService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("scheduleRequestDTO", new ScheduleRequestDTO());
        model.addAttribute("persons", personService.findAll());
        return "schedule";
    }

    @GetMapping("/showSchedule")
    public String showSchedule(ScheduleRequestDTO scheduleRequestDTO, Model model) {
        LocalDate from = LocalDate.parse(scheduleRequestDTO.getDateFrom());
        LocalDate to = LocalDate.parse(scheduleRequestDTO.getDateTo());
        Person person = personService.findPerson(scheduleRequestDTO);

        model.addAttribute("schedule", scheduleService.getSchedule(person, from, to));
        model.addAttribute("searchedPerson", person);


        List<Person> persons = personService.findAll();
        persons.remove(person);
        persons.add(0, person);
        model.addAttribute("persons", persons);
        return "schedule";
    }

    @ModelAttribute("courseService")
    public CourseService courseService() {
        return courseService;
    }

}
