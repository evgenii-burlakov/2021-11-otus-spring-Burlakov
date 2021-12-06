package ru.otus.spring.domain;

import java.util.Objects;

public class User {
    private String firstName;
    private String secondNuestion;
    private String surname;

    public User(String firstName, String secondNuestion, String surname) {
        this.firstName = firstName;
        this.secondNuestion = secondNuestion;
        this.surname = surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondNuestion() {
        return secondNuestion;
    }

    public void setSecondNuestion(String secondNuestion) {
        this.secondNuestion = secondNuestion;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(firstName, user.firstName) && Objects.equals(secondNuestion, user.secondNuestion) && Objects.equals(surname, user.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, secondNuestion, surname);
    }
}
