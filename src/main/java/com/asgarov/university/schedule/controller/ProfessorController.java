package com.asgarov.university.schedule.controller;

import com.asgarov.university.schedule.dao.exception.DaoException;
import com.asgarov.university.schedule.domain.Professor;
import com.asgarov.university.schedule.service.ProfessorService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
@RequestMapping("professor")
public class ProfessorController {

    private ProfessorService professorService;

    private final static String CANT_DELETE_MESSAGE = "Can't delete, as this Professor is still registered for course(s)!";


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
        } catch (EmptyResultDataAccessException e) {
            // Nothing found under the id - nothing to handle
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
        try {
            professorService.deleteById(id);
        } catch (DataIntegrityViolationException | DaoException e) {
            model.addAttribute("error", CANT_DELETE_MESSAGE);
            model.addAttribute("professors", professorService.findAll());
            return "professor";
        }
        return "redirect:/professor";
    }

    @PutMapping("/{id}")
    public String updateProfessor(@PathVariable Long id, Professor professor) throws DaoException {
        professor.setId(id);
        professorService.update(professor);
        return "redirect:/professor";
    }

    @ModelAttribute("newProfessor")
    public Professor newProfessor() {
        return new Professor();
    }
}
