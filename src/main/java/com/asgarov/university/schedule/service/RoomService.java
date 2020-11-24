package com.asgarov.university.schedule.service;

import com.asgarov.university.schedule.domain.Lecture;
import com.asgarov.university.schedule.domain.Room;
import com.asgarov.university.schedule.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoomService extends AbstractService<Room, Long> {

    private static final int LESSON_TIME_IN_MINUTES = 90;
    private final LectureService lectureService;
    private final RoomRepository roomRepository;

    public RoomService(final RoomRepository roomRepository, final LectureService lectureService) {
        super(roomRepository);
        this.roomRepository = roomRepository;
        this.lectureService = lectureService;
    }

    public boolean isRoomAvailable(Room room, LocalDateTime dateTime) {
        return lectureService.findAll()
                .stream()
                .noneMatch(lecture -> isLectureAtThatDateTime(lecture, room, dateTime));

    }

    private boolean isLectureAtThatDateTime(Lecture lecture, Room room, LocalDateTime dateTime) {
        if (!lecture.getRoom().equals(room)) {
            return false;
        }
        return Duration.between(lecture.getDateTime(), dateTime).toMinutes() < LESSON_TIME_IN_MINUTES;
    }

    public List<Room> findAll() {
        return roomRepository.findAll();
    }
}
