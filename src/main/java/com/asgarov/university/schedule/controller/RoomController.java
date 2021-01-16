package com.asgarov.university.schedule.controller;

import com.asgarov.university.schedule.dao.exception.DaoException;
import com.asgarov.university.schedule.domain.Room;
import com.asgarov.university.schedule.service.RoomService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
@RequestMapping("room")
public class RoomController {

    private RoomService roomService;
    private final static String CANT_DELETE_MESSAGE = "Can't delete, as this room is being used for lecture(s)!";

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("rooms", roomService.findAll());
        return "room";
    }

    @GetMapping("/searchRoomsById")
    public String searchRoomsById(@RequestParam Long id, Model model) {
        try {
            model.addAttribute("rooms", Collections.singletonList(roomService.findById(id)));
        } catch (EmptyResultDataAccessException e) {
            // Nothing found under the id - nothing to handle
        }
        return "room";
    }

    @PostMapping
    public String addNewRoom(Room room) {
        roomService.create(room);
        return "redirect:/room";
    }

    @DeleteMapping("/{id}")
    public String deleteRoom(@PathVariable Long id, Model model) {
        try {
            roomService.deleteById(id);
        } catch (DataIntegrityViolationException | DaoException e) {
            model.addAttribute("error", CANT_DELETE_MESSAGE);
            model.addAttribute("rooms", roomService.findAll());
            return "room";
        }
        return "redirect:/room";
    }

    @PutMapping("/{id}")
    public String updateRoom(@PathVariable Long id, @RequestParam String roomName) {
        Room room = roomService.findById(id);
        room.setName(roomName);
        try {
            roomService.update(room);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return "redirect:/room";
    }

    @ModelAttribute("newRoom")
    public Room newRoom() {
        return new Room();
    }

}
