package com.asgarov.university.schedule.domain;

public class Professor extends Person {

    {
        role = Role.PROFESSOR;
    }

    public Professor() {
    }

    public Professor(final String firstName, final String lastName, final String email, final String pass) {
        super(firstName, lastName, email, pass);
    }

    public Professor(final String firstName, final String lastName) {
        super(firstName, lastName, lastName.toLowerCase() + "@mail.ru", "pass");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "Professor " + firstName + " " + lastName;
    }
}
