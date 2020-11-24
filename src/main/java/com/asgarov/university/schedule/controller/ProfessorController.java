package com.asgarov.university.schedule.controller;

import com.asgarov.university.schedule.domain.Professor;
import com.asgarov.university.schedule.service.ProfessorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;

@Controller
@RequestMapping("professor")
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("professors", professorService.findAll());
        return "professor";
    }

    @GetMapping("/searchProfessorsById")
    public String searchProfessorsById(@RequestParam Long id, Model model) {
        try {
            model.addAttribute("professors", Collections.singletonList(professorService.findById(id)));
        } catch (EntityNotFoundException e) {
            //nothing to handle
        }
        return "professor";
    }

    @PostMapping
    public String addNewProfessor(Professor professor) {
        professorService.create(professor);
        return "redirect:/professor";
    }

    @DeleteMapping("/{id}")
    public String deleteProfessor(@PathVariable Long id, Model model) {
        professorService.deleteById(id);
        return "redirect:/professor";
    }

    @PutMapping("/{id}")
    public String updateProfessor(@PathVariable Long id, Professor professor) {
        professor.setId(id);
        professorService.update(professor);
        return "redirect:/professor";
    }

    @ModelAttribute("newProfessor")
    public Professor newProfessor() {
        return new Professor();
    }
}
