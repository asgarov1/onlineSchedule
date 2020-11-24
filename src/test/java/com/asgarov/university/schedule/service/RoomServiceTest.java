package com.asgarov.university.schedule.service;

import com.asgarov.university.schedule.domain.Lecture;
import com.asgarov.university.schedule.domain.Room;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RoomServiceTest {

    @Autowired
    RoomService roomService;

    @Autowired
    LectureService lectureService;

    @Test
    void createShouldWork() {
        Room room = new Room("A111");
        Long roomId = roomService.create(room).getId();
        room.setId(roomId);

        Room expected = room;
        Room actual = roomService.findById(roomId);
        assertEquals(expected, actual);
    }

    @Test
    void updateShouldWork() {
        Room room = roomService.findAll().get(0);
        room.setName("B999");

        roomService.update(room);

        Room actualRoom = roomService.findById(room.getId());
        assertEquals(room, actualRoom);
    }

    @Test
    void findAllShouldWork() {
        List<Room> rooms = roomService.findAll();
        assertNotNull(rooms);
    }

    @Test
    void findByIdShouldWork() {
        List<Room> rooms = roomService.findAll();
        Room expected = rooms.get(0);
        Room actual = roomService.findById(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    void deleteByIdShouldWork() {
        Room room = new Room("C333");
        Long professorId = roomService.create(room).getId();

        int sizeBeforeDelete = roomService.findAll().size();

        roomService.deleteById(professorId);

        int expectedSize = sizeBeforeDelete - 1;
        assertEquals(expectedSize, roomService.findAll().size());
    }

    @Test
    void isRoomAvailable() {
        Room room = roomService.findAll().get(0);
        assertTrue(roomService.isRoomAvailable(room, LocalDateTime.of(2025, Month.APRIL, 15, 11, 30)));

        Lecture lecture = lectureService.findAll().get(0);
        assertFalse(roomService.isRoomAvailable(lecture.getRoom(), lecture.getDateTime()));
    }
}
