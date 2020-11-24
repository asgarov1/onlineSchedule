package com.asgarov.university.schedule.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    private LocalDateTime dateTime;

    @ManyToOne
    private Room room;

    public Lecture() {
    }

    public Lecture(LocalDateTime dateTime, Room room) {
        this.dateTime = dateTime;
        this.room = room;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lecture)) return false;

        Lecture lecture = (Lecture) o;

        if (getId() != null ? !getId().equals(lecture.getId()) : lecture.getId() != null) return false;
        if (getDateTime() != null ? !getDateTime().equals(lecture.getDateTime()) : lecture.getDateTime() != null)
            return false;
        return getRoom() != null ? getRoom().equals(lecture.getRoom()) : lecture.getRoom() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getDateTime() != null ? getDateTime().hashCode() : 0);
        result = 31 * result + (getRoom() != null ? getRoom().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Lecture{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", room=" + room +
                '}';
    }
}
