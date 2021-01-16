package com.asgarov.university.schedule.controller;

import com.asgarov.university.schedule.dao.exception.DaoException;
import com.asgarov.university.schedule.domain.Course;
import com.asgarov.university.schedule.domain.Lecture;
import com.asgarov.university.schedule.domain.Room;
import com.asgarov.university.schedule.domain.dto.LectureDTO;
import com.asgarov.university.schedule.service.CourseService;
import com.asgarov.university.schedule.service.LectureService;
import com.asgarov.university.schedule.service.RoomService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("lecture")
public class LectureController {

    private LectureService lectureService;
    private RoomService roomService;
    private CourseService courseService;
    private final static Integer AMOUNT_PER_PAGE = 10;

    public LectureController(LectureService lectureService, RoomService roomService, CourseService courseService) {
        this.lectureService = lectureService;
        this.roomService = roomService;
        this.courseService = courseService;
    }

    @GetMapping
    public String index(Model model,
                        @RequestParam(required = false) Integer page,
                        @RequestParam(required = false) Integer size) {

        int currentPage = 1;
        int pageSize = AMOUNT_PER_PAGE;

        if (page != null) {
            currentPage = page;
        }
        if (size != null) {
            pageSize = size;
        }

        Page<Lecture> lecturePage = lectureService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("lecturePage", lecturePage);
        model.addAttribute("lectureDTO", new LectureDTO());

        int totalPages = lecturePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "lecture";
    }

    @GetMapping("/searchLecturesById")
    public String searchLecturesById(@RequestParam Long id, Model model) {
        try {
            model.addAttribute("lectures", Collections.singletonList(lectureService.findById(id)));
            model.addAttribute("lectureDTO", new LectureDTO());
        } catch (EmptyResultDataAccessException e) {
            // Nothing found under the id - nothing to handle
        }
        return "lecture";
    }

    @PostMapping
    public String addNewLecture(LectureDTO lectureDTO) {
        LocalDate localDate = LocalDate.parse(lectureDTO.getDate());
        LocalTime localTime = LocalTime.parse(lectureDTO.getTime());
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        Long lectureId = lectureService.create(new Lecture(localDateTime, roomService.findById(lectureDTO.getRoomId())));
        courseService.scheduleLecture(lectureDTO.getCourseId(), lectureId);
        return "redirect:/lecture";
    }

    @DeleteMapping("/{id}")
    public String deleteLecture(@PathVariable Long id) throws DaoException {
        lectureService.deleteById(id);
        return "redirect:/lecture";
    }

    @PutMapping("/{id}")
    public String updateLecture(@PathVariable Long id, LectureDTO lectureDTO) throws DaoException {
        Lecture lecture = lectureService.findById(id);
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.parse(lectureDTO.getDate()), LocalTime.parse(lectureDTO.getTime()));
        lecture.setDateTime(dateTime);
        lecture.setRoom(roomService.findById(lectureDTO.getRoomId()));
        lectureService.update(lecture);
        return "redirect:/lecture";
    }

    @ModelAttribute("courseService")
    public CourseService courseService() {
        return courseService;
    }

    @ModelAttribute("rooms")
    public List<Room> rooms() {
        return roomService.findAll();
    }

    @ModelAttribute("courses")
    public List<Course> courses() {
        return courseService.findAll();
    }
}
