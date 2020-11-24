package com.asgarov.university.schedule.domain.dto;

public class LectureDTO {
    private String date;
    private String time;
    private Long roomId;
    private Long courseId;

    public LectureDTO() {
    }

    public LectureDTO(String date, String time, Long roomId, Long courseId) {
        this.date = date;
        this.time = time;
        this.roomId = roomId;
        this.courseId = courseId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
