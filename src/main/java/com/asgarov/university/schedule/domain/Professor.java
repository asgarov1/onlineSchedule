package com.asgarov.university.schedule.domain;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;

@Entity
public class Professor extends Person {

    {
        role = Role.PROFESSOR;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private List<Course> courses;

    public Professor() {
    }

    public Professor(final String firstName, final String lastName, final String email, final String pass) {
        super(firstName, lastName, email, pass);
    }

    public Professor(final String firstName, final String lastName) {
        super(firstName, lastName, lastName.toLowerCase() + "@mail.ru", "pass");
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Professor)) return false;
        if (!super.equals(o)) return false;

        Professor professor = (Professor) o;

        return Objects.equals(courses, professor.courses);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "Professor{" +
                "id=" + getId() +
                ", role=" + role +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
