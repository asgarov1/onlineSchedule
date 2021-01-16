package com.asgarov.university.schedule.domain;

public class CourseStudent {

    private long id;
    private long courseId;
    private long studentId;

    public CourseStudent() {
    }

    public CourseStudent(final long course_id, final long student_id) {
        this.courseId = course_id;
        this.studentId = student_id;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(final long courseId) {
        this.courseId = courseId;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(final long studentId) {
        this.studentId = studentId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        final CourseStudent that = (CourseStudent) o;

        if (id != that.id)
            return false;
        if (courseId != that.courseId)
            return false;
        return studentId == that.studentId;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (courseId ^ (courseId >>> 32));
        result = 31 * result + (int) (studentId ^ (studentId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "CourseStudent{" +
                "id=" + id +
                ", course_id=" + courseId +
                ", student_id=" + studentId +
                '}';
    }
}
