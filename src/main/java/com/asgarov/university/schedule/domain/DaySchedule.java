package com.asgarov.university.schedule.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DaySchedule {

    private List<Lecture> lectures = new ArrayList<>();
    private LocalDate localDate;

    public DaySchedule() {
    }

    public DaySchedule(LocalDate localDate, List<Lecture> lectures) {
        this.localDate = localDate;
        this.lectures = lectures;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        final DaySchedule that = (DaySchedule) o;

        if (!Objects.equals(lectures, that.lectures))
            return false;
        return Objects.equals(localDate, that.localDate);
    }

    @Override
    public int hashCode() {
        int result = lectures != null ? lectures.hashCode() : 0;
        result = 31 * result + (localDate != null ? localDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DaySchedule{" +
                "lectures=" + lectures +
                ", localDate=" + localDate +
                '}';
    }
}
