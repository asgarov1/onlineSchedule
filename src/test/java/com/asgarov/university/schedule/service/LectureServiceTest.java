package com.asgarov.university.schedule.service;

import com.asgarov.university.schedule.domain.Lecture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LectureServiceTest {

    @Autowired
    LectureService lectureService;

    @Autowired
    RoomService roomService;

    @Test
    void createShouldWork() {
        Lecture lecture = new Lecture(LocalDateTime.now().plusDays(2), roomService.findAll().get(0));
        Long lectureId = lectureService.create(lecture).getId();

        assertNotNull(lectureService.findById(lectureId));
    }

    @Test
    void updateShouldWork() {
        List<Lecture> lectures = lectureService.findAll();
        Lecture lecture = lectures.get(0);
        lecture.setDateTime(LocalDateTime.now().plusDays(1));

        lectureService.update(lecture);

        Lecture actualLecture = lectureService.findById(lecture.getId());
        assertEquals(lecture.getDateTime().getDayOfYear(), actualLecture.getDateTime().getDayOfYear());
    }

    @Test
    void findByIdShouldWork() {
        List<Lecture> lectures = lectureService.findAll();
        Lecture lecture = lectures.get(0);
        Lecture actual = lectureService.findById(lecture.getId());
        assertNotNull(actual);
    }

    @Test
    void findAllShouldWork() {
        List<Lecture> lectures = lectureService.findAll();
        assertNotNull(lectures);
    }

    @Test
    void deleteByIdShouldWork() {
        List<Lecture> lectures = lectureService.findAll();

        Long lectureId = lectures.get(0).getId();
        lectureService.deleteById(lectureId);

        int expectedSize = lectures.size() - 1;
        int actualSize = lectureService.findAll().size();
        assertEquals(expectedSize, actualSize);
    }
}
