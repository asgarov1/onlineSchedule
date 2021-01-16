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

import java.util.List;

@Controller
@RequestMapping("schedule")
public class ScheduleController {

    private ScheduleService scheduleService;
    private CourseService courseService;
    private PersonService personService;

    public ScheduleController(ScheduleService scheduleService, CourseService courseService, PersonService personService) {
        this.scheduleService = scheduleService;
        this.courseService = courseService;
        this.personService = personService;
    }


    @GetMapping
    public String index(Model model) {
        model.addAttribute("scheduleRequestDTO", new ScheduleRequestDTO());
        return "schedule";
    }

    @GetMapping("/showSchedule")
    public String showSchedule(ScheduleRequestDTO scheduleRequestDTO, Model model) {
        model.addAttribute("schedule", scheduleService.getSchedule(scheduleRequestDTO));
        return "schedule";
    }

    @ModelAttribute("courseService")
    public CourseService courseService() {
        return courseService;
    }

    @ModelAttribute("persons")
    public List<Person> persons() {
        return personService.findAll();
    }

}
