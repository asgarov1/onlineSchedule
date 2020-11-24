package com.asgarov.university.schedule.controller;

import com.asgarov.university.schedule.domain.Room;
import com.asgarov.university.schedule.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;

@Controller
@RequestMapping("room")
public class RoomController {

    private final RoomService roomService;

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
        } catch (EntityNotFoundException e) {
            //nothing to handle
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
        roomService.deleteById(id);
        return "redirect:/room";
    }

    @PutMapping("/{id}")
    public String updateRoom(@PathVariable Long id, @RequestParam String roomName) {
        Room room = roomService.findById(id);
        room.setName(roomName);
        roomService.update(room);
        return "redirect:/room";
    }

    @ModelAttribute("newRoom")
    public Room newRoom() {
        return new Room();
    }

}
