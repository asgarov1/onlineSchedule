package com.asgarov.university.schedule.domain;

import java.util.Objects;

public class CourseLecture {

    private Long id;
    private Long courseId;
    private Long lectureId;

    public CourseLecture() {
    }

    public CourseLecture(final Long courseId, final Long lectureId) {
        this.courseId = courseId;
        this.lectureId = lectureId;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(final Long courseId) {
        this.courseId = courseId;
    }

    public Long getLectureId() {
        return lectureId;
    }

    public void setLectureId(final Long lectureId) {
        this.lectureId = lectureId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        final CourseLecture that = (CourseLecture) o;

        if (!Objects.equals(id, that.id))
            return false;
        if (!Objects.equals(courseId, that.courseId))
            return false;
        return Objects.equals(lectureId, that.lectureId);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (courseId != null ? courseId.hashCode() : 0);
        result = 31 * result + (lectureId != null ? lectureId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CourseLectures{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", lectureId=" + lectureId +
                '}';
    }
}
