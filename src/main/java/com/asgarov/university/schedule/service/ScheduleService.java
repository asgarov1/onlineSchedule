package com.asgarov.university.schedule.service;

import com.asgarov.university.schedule.domain.DaySchedule;
import com.asgarov.university.schedule.domain.LectureView;
import com.asgarov.university.schedule.domain.Role;
import com.asgarov.university.schedule.domain.dto.ScheduleRequestDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Service
public class ScheduleService {
    private final LectureService lectureService;

    public ScheduleService(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    public List<DaySchedule> getSchedule(ScheduleRequestDTO request) {
        LocalDate from = LocalDate.parse(request.getDateFrom());
        LocalDate to = LocalDate.parse(request.getDateTo());

        if (request.getRole().equals(Role.STUDENT.toString())) {
            return lectureService.findAllLectureForStudent(request.getId(), from, to)
                    .stream().collect(groupingBy(LectureView::getDateTime))
                    .entrySet().stream().map(entry -> new DaySchedule(entry.getKey().toLocalDate(), entry.getValue()))
                    .collect(toList());
        } else {
            return lectureService.findAllLectureForProfessor(request.getId(), from, to)
                    .stream().collect(groupingBy(LectureView::getDateTime))
                    .entrySet().stream().map(entry -> new DaySchedule(entry.getKey().toLocalDate(), entry.getValue()))
                    .collect(toList());
        }
    }

}
