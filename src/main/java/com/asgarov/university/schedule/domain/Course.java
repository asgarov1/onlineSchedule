package com.asgarov.university.schedule.domain;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.EAGER)
    @JoinTable(name = "course_student",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> registeredStudents = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.EAGER)
    private Professor professor;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany
    private List<Lecture> lectures = new ArrayList<>();

    public Course() {
    }

    public Course(String name) {
        this.name = name;
    }

    public Course(String name, List<Student> registeredStudents, Professor professor, List<Lecture> lectures) {
        this.name = name;
        this.registeredStudents = registeredStudents;
        this.professor = professor;
        this.lectures = lectures;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getRegisteredStudents() {
        return registeredStudents;
    }

    public void setRegisteredStudents(List<Student> registeredStudents) {
        this.registeredStudents = registeredStudents;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    public void addStudent(Student student) {
        registeredStudents.add(student);
    }

    public void addLecture(Lecture lecture) {
        lectures.add(lecture);
    }

    public void removeStudent(Student student) {
        registeredStudents.remove(student);
    }

    public void removeLecture(Lecture lecture) {
        lectures.remove(lecture);
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
        if (!(o instanceof Course)) return false;

        Course course = (Course) o;

        if (getId() != null ? !getId().equals(course.getId()) : course.getId() != null) return false;
        if (getName() != null ? !getName().equals(course.getName()) : course.getName() != null) return false;
        if (getRegisteredStudents() != null ? !getRegisteredStudents().equals(course.getRegisteredStudents()) : course.getRegisteredStudents() != null)
            return false;
        if (getProfessor() != null ? !getProfessor().equals(course.getProfessor()) : course.getProfessor() != null)
            return false;
        return getLectures() != null ? getLectures().equals(course.getLectures()) : course.getLectures() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getRegisteredStudents() != null ? getRegisteredStudents().hashCode() : 0);
        result = 31 * result + (getProfessor() != null ? getProfessor().hashCode() : 0);
        result = 31 * result + (getLectures() != null ? getLectures().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", registeredStudents=" + registeredStudents +
                ", professor=" + professor +
                ", lectures=" + lectures +
                '}';
    }
}
